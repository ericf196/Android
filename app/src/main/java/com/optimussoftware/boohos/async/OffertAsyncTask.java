package com.optimussoftware.boohos.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.optimussoftware.api.DislikeResource;
import com.optimussoftware.api.LikeResource;
import com.optimussoftware.api.response.CheckResponse;
import com.optimussoftware.api.response.GenericResponse;
import com.optimussoftware.db.Advertising;
import com.optimussoftware.db.Dislikes;
import com.optimussoftware.db.Likes;
import com.optimussoftware.boohos.data.DBController;
import com.optimussoftware.boohos.data.PersonalInfo;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Perez on 21/7/2016.
 */
public class OffertAsyncTask extends AsyncTask<Void, Void, Boolean> {
    
    private static final String TAG = OffertAsyncTask.class.getSimpleName();

    private PersonalInfo personalInfo;
    private Context context;
    private Likes likes;
    private Dislikes dislikes;
    private Advertising advertising;
    private boolean value;
    private DBController dbController;
    private Object sync = new Object();

    public OffertAsyncTask(Context context, PersonalInfo personalInfo, Likes likes, Dislikes dislikes, Advertising advertising, boolean value) {
        this.context = context;
        this.personalInfo = personalInfo;
        this.likes = likes;
        this.dislikes = dislikes;
        this.advertising = advertising;
        dbController = DBController.getControler();
        this.value = value;
    }

    private void addLike(final String uid) {
        Callback<GenericResponse> createLikesCallback = new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

                try {
                    if (response.isSuccessful()) {
                        GenericResponse r = response.body();
                        Likes likes = new Likes();
                        likes.set_id(r.get_id());
                        likes.set_etag(r.get_etag());
                        likes.setActive(true);
                        likes.setDeleted(false);
                        likes.setAdvertising_id(uid);
                        likes.setUser_id(personalInfo.getUuid());
                        dbController.createLike(context, likes);

                        Log.i(TAG, "Add Like --> " + response.body().get_id());
                        if (checkIfDislikes(context, advertising.get_id(), personalInfo.getUuid()) != null) {
                            dbController.removeDislike(context, checkIfDislikes(context, advertising.get_id(), personalInfo.getUuid()));
                        }
                    } else {
                        checkIfExistLike(uid, true);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {
                Log.i(TAG, "Err callback addlike --> " + t.getCause().toString());
                checkIfExistLike(uid, true);
            }
        };

        LikeResource likeResource = new LikeResource();
        Likes likes = new Likes();
        likes.setAdvertising_id(uid);
        likes.setUser_id(personalInfo.getUuid());
        likeResource.like(likes, createLikesCallback);
    }

    private void checkIfExistLike(final String advertising_id, final boolean valor) {
        Log.i(TAG, "Check Existe");
        Callback<CheckResponse> checkIfExistLikeCallback = new Callback<CheckResponse>() {
            @Override
            public void onResponse(Call<CheckResponse> call, Response<CheckResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        Log.i(TAG, "Check exitoso");
                        Log.i(TAG, "Agregar Likes " + response.body().getId());
                        CheckResponse r = response.body();
                        Likes rLikes = new Likes();
                        rLikes.set_id(r.getId());
                        rLikes.set_etag(r.getSource().getEtag());
                        rLikes.setActive(true);
                        rLikes.setDeleted(false);
                        rLikes.setAdvertising_id(advertising_id);
                        rLikes.setUser_id(personalInfo.getUuid());
                        if (valor) {

                            dbController.createLike(context, rLikes);
                            Log.i(TAG, "CheckiIfExistLike en el servidor add BD del movil --> " + response.body().getId());
                        } else {
                            //  removeLike(response.body().get_id(), response.body().get_etag());
                            if (checkIfLikes(context, rLikes.getAdvertising_id(), personalInfo.getUuid()) != null) {
                                dbController.removeLike(context, rLikes);
                                Log.i(TAG, "Remover likes check bd movil --> " + response.body().getId());
                            }
                            Log.i(TAG, "Remover likes check servidor" + response.body().getId());
                        }

                    } else {
                        Log.i(TAG, "Check Fail");
                    }
                } catch (Exception e) {
                    //  Log.e(TAG, "Err check like--> " + e + " valor " + response.body().getSource().getEtag());
                }
            }

            @Override
            public void onFailure(Call<CheckResponse> call, Throwable t) {
                Log.i(TAG, "Err callback checklike --> " + t.getMessage());
            }
        };

        Likes likes = new Likes();
        likes.setAdvertising_id(advertising_id);
        likes.setUser_id(personalInfo.getUuid());
        LikeResource likeResource = new LikeResource();
        likeResource.checkIfExist(likes, checkIfExistLikeCallback);
    }

    public void removeLike(String uid, String e_tag) {
        Callback<Void> removeDislikeCallback = new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                dbController.removeLike(context, likes);
                Log.i(TAG, "RemoveLike Response --> Void");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i(TAG, "Err removerlike --> " + t.getMessage());
            }
        };

        LikeResource likeResource = new LikeResource();
        Likes likes = new Likes();
        likes.set_id(uid);
        likes.set_etag(e_tag);
        likeResource.remove(likes, removeDislikeCallback);
    }

    private void addDislike(final String uid) {
        Callback<GenericResponse> createDislikesCallback = new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        GenericResponse r = response.body();
                        Dislikes dislikes = new Dislikes();
                        dislikes.set_id(r.get_id());
                        dislikes.set_etag(r.get_etag());
                        dislikes.setAdvertising_id(uid);
                        dislikes.setUser_id(personalInfo.getUuid());
                        dislikes.setActive(true);
                        dislikes.setDeleted(false);
                        dbController.createDislike(context, dislikes);
                        Log.i(TAG, "Add DisLike --> " + response.body().get_id());
                        if (checkIfLikes(context, advertising.get_id(), personalInfo.getUuid()) != null) {
                            dbController.removeLike(context, checkIfLikes(context, advertising.get_id(), personalInfo.getUuid()));
                        }
                    } else {
                        checkIfExistDislike(uid, true);
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {
                Log.i(TAG, "Err callback Add DisLike --> " + t.getMessage());
                checkIfExistDislike(uid, true);
            }
        };


        DislikeResource dislikeResource = new DislikeResource();
        Dislikes dislikes = new Dislikes();
        dislikes.setAdvertising_id(uid);
        dislikes.setUser_id(personalInfo.getUuid());
        dislikeResource.dislike(dislikes, createDislikesCallback);
    }

    private void checkIfExistDislike(final String advertising_id, final boolean valor) {
        Callback<CheckResponse> checkIfExistDislikeCallback = new Callback<CheckResponse>() {
            @Override
            public void onResponse(Call<CheckResponse> call, Response<CheckResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        Log.i(TAG, "Agregar Dislike" + response.body().getId());
                        CheckResponse r = response.body();
                        Dislikes rDislikes = new Dislikes();
                        rDislikes.set_id(r.getId());
                        rDislikes.set_etag(r.getSource().getEtag());
                        rDislikes.setAdvertising_id(advertising_id);
                        rDislikes.setUser_id(personalInfo.getUuid());
                        rDislikes.setActive(true);
                        rDislikes.setDeleted(false);

                        if (valor) {

                            dbController.createDislike(context, rDislikes);
                            //Log.i(TAG, "CheckIfExistDisLike en el server, agregar a la bd del movil--> " + response.body().get_id());
                        } else {
                            removeDislike(response.body().getId(), response.body().getSource().getEtag());
                            //Log.i(TAG, "CheckIfExistDislike removeDislike --> " + response.body().get_id());
                            if (checkIfDislikes(context, response.body().getId(), personalInfo.getUuid()) != null) {
                                dbController.removeDislike(context, rDislikes);
                                //Log.i(TAG, "CheckIfExistDislike en el movil remover --> " + response.body().get_id());
                            }
                        }
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Err checkIfDislikes -->" + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<CheckResponse> call, Throwable t) {
                Log.i(TAG, "Err callback checkifexistdislike --> " + t.getMessage());
            }
        };
        DislikeResource dislikeResource = new DislikeResource();
        Dislikes dislikes = new Dislikes();
        dislikes.setAdvertising_id(advertising_id);
        dislikes.setUser_id(personalInfo.getUuid());
        dislikeResource.checkIfExist(dislikes, checkIfExistDislikeCallback);
    }

    public void removeDislike(String uid, String e_tag) {
        Callback<Void> removeDislikeCallback = new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                dbController.removeDislike(context, dislikes);
                Log.i(TAG, "Remover dislikes");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i(TAG, "Err remover dislikes --> " + t.getMessage());
            }
        };

        DislikeResource dislikeResource = new DislikeResource();
        Dislikes dislikes = new Dislikes();
        dislikes.set_etag(e_tag);
        dislikes.set_id(uid);
        dislikeResource.remove(dislikes, removeDislikeCallback);
    }

    public Likes checkIfLikes(Context context, String advertising_id, String user_id) {
        List<Likes> likesList = dbController.getLikesByUser(context, user_id, advertising_id);
        Likes likes = null;
        for (int i = 0; i < likesList.size(); i++) {
            likes = likesList.get(i);
        }
        return likes;
    }

    public Dislikes checkIfDislikes(Context context, String advertising_id, String user_id) {
        List<Dislikes> dislikesList = dbController.getDislikesByUser(context, user_id, advertising_id);
        Dislikes dislikes = null;
        for (int i = 0; i < dislikesList.size(); i++) {
            dislikes = dislikesList.get(i);
        }
        return dislikes;
    }

    private boolean syncProccess() throws IOException {
        if (value) {
            if (likes == null) {
                addLike(advertising.get_id());
            } else {
                removeLike(likes.get_id(), likes.get_etag());
            }
            return true;
        } else {
            if (dislikes == null) {
                addDislike(advertising.get_id());
            } else {
                removeDislike(dislikes.get_id(), dislikes.get_etag());
            }
        }
        return true;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        synchronized (sync) {
            try {
                boolean sw =
                        syncProccess();
                if (sw) {
                }
                return sw;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }
}
