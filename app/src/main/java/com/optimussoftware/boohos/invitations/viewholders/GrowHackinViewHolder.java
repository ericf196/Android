package com.optimussoftware.boohos.invitations.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.view.IconicsImageView;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.invitations.GrowHackingActivity;
import com.optimussoftware.boohos.invitations.GrowHackingModel;
import com.optimussoftware.boohos.widget.OptimusTextView;
import com.optimussoftware.image.Factory;
import com.optimussoftware.image.SizeImage;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by leonardojpr on 12/7/16.
 */

public class GrowHackinViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = GrowHackinViewHolder.class.getSimpleName();

    private Context context;
    private OptimusTextView message;
    private OptimusTextView name;
    private OptimusTextView country;
    private CircleImageView img_perfil;
    private IconicsImageView btn_add;
    public LinearLayout content;

    public GrowHackinViewHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
        message = (OptimusTextView) itemView.findViewById(R.id.mensaje_grow_hacking);
        name = (OptimusTextView) itemView.findViewById(R.id.name_contact);
        country = (OptimusTextView) itemView.findViewById(R.id.country_contact);
        img_perfil = (CircleImageView) itemView.findViewById(R.id.profile_image);
        btn_add = (IconicsImageView) itemView.findViewById(R.id.icon_add);
        content = (LinearLayout) itemView.findViewById(R.id.content);

    }

    public void setData(GrowHackingModel model, int from) {
        name.setText(model.getName());
        country.setText(model.getCountry());
        country.setVisibility(View.GONE);
        Log.d(TAG, "isSelect -->" + model.isSelect());
        SizeImage sizeImage = new SizeImage(70, 70);
        Log.d(TAG, "image -->" + model.getImg_profile());
        if (model.getImg_profile() != null)
            Factory.setImage(img_perfil, context, model.getImg_profile(), sizeImage);
        else
            img_perfil.setImageResource(R.drawable.ic_profile_image);

        if (model.isSelect()) {
            setIcon(3);
        } else {
            setIcon(from);
        }

    }

    public void setIcon(int from) {
        if (GrowHackingActivity.CONNECT_FRIEND == from)
            btn_add.setIcon(CommunityMaterial.Icon.cmd_plus_circle);
        if (GrowHackingActivity.INVITE_APP == from)
            btn_add.setIcon(CommunityMaterial.Icon.cmd_email);
        if (GrowHackingActivity.SELECT == from)
            btn_add.setIcon(CommunityMaterial.Icon.cmd_check);

    }

    public void showMessage(boolean isShow) {
        if (isShow)
            message.setVisibility(View.VISIBLE);
        else
            message.setVisibility(View.GONE);
    }
}
