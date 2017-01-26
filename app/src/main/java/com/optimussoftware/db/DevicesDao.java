package com.optimussoftware.db;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.SqlUtils;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "DEVICES".
*/
public class DevicesDao extends AbstractDao<Devices, String> {

    public static final String TABLENAME = "DEVICES";

    /**
     * Properties of entity Devices.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property _id = new Property(0, String.class, "_id", true, "_ID");
        public final static Property _etag = new Property(1, String.class, "_etag", false, "_ETAG");
        public final static Property Created = new Property(2, java.util.Date.class, "created", false, "CREATED");
        public final static Property Updated = new Property(3, java.util.Date.class, "updated", false, "UPDATED");
        public final static Property Active = new Property(4, Boolean.class, "active", false, "ACTIVE");
        public final static Property Deleted = new Property(5, Boolean.class, "deleted", false, "DELETED");
        public final static Property Name_key = new Property(6, String.class, "name_key", false, "NAME_KEY");
        public final static Property Description = new Property(7, String.class, "description", false, "DESCRIPTION");
        public final static Property Uuid = new Property(8, String.class, "uuid", false, "UUID");
        public final static Property Mac = new Property(9, String.class, "mac", false, "MAC");
        public final static Property Major = new Property(10, Integer.class, "major", false, "MAJOR");
        public final static Property Minor = new Property(11, Integer.class, "minor", false, "MINOR");
        public final static Property Rssi = new Property(12, Integer.class, "rssi", false, "RSSI");
        public final static Property Namespace = new Property(13, Integer.class, "namespace", false, "NAMESPACE");
        public final static Property Instance = new Property(14, Integer.class, "instance", false, "INSTANCE");
        public final static Property Type = new Property(15, Integer.class, "type", false, "TYPE");
        public final static Property Url = new Property(16, String.class, "url", false, "URL");
        public final static Property Message_for_user = new Property(17, String.class, "message_for_user", false, "MESSAGE_FOR_USER");
        public final static Property Is_single = new Property(18, Boolean.class, "is_single", false, "IS_SINGLE");
        public final static Property Location_id = new Property(19, String.class, "location_id", false, "LOCATION_ID");
    }

    private DaoSession daoSession;

    private Query<Devices> location_TheLocationDeviceQuery;

    public DevicesDao(DaoConfig config) {
        super(config);
    }
    
    public DevicesDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"DEVICES\" (" + //
                "\"_ID\" TEXT PRIMARY KEY NOT NULL UNIQUE ," + // 0: _id
                "\"_ETAG\" TEXT," + // 1: _etag
                "\"CREATED\" INTEGER," + // 2: created
                "\"UPDATED\" INTEGER," + // 3: updated
                "\"ACTIVE\" INTEGER," + // 4: active
                "\"DELETED\" INTEGER," + // 5: deleted
                "\"NAME_KEY\" TEXT," + // 6: name_key
                "\"DESCRIPTION\" TEXT," + // 7: description
                "\"UUID\" TEXT," + // 8: uuid
                "\"MAC\" TEXT," + // 9: mac
                "\"MAJOR\" INTEGER," + // 10: major
                "\"MINOR\" INTEGER," + // 11: minor
                "\"RSSI\" INTEGER," + // 12: rssi
                "\"NAMESPACE\" INTEGER," + // 13: namespace
                "\"INSTANCE\" INTEGER," + // 14: instance
                "\"TYPE\" INTEGER," + // 15: type
                "\"URL\" TEXT," + // 16: url
                "\"MESSAGE_FOR_USER\" TEXT," + // 17: message_for_user
                "\"IS_SINGLE\" INTEGER," + // 18: is_single
                "\"LOCATION_ID\" TEXT NOT NULL );"); // 19: location_id
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"DEVICES\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Devices entity) {
        stmt.clearBindings();
        stmt.bindString(1, entity.get_id());
 
        String _etag = entity.get_etag();
        if (_etag != null) {
            stmt.bindString(2, _etag);
        }
 
        java.util.Date created = entity.getCreated();
        if (created != null) {
            stmt.bindLong(3, created.getTime());
        }
 
        java.util.Date updated = entity.getUpdated();
        if (updated != null) {
            stmt.bindLong(4, updated.getTime());
        }
 
        Boolean active = entity.getActive();
        if (active != null) {
            stmt.bindLong(5, active ? 1L: 0L);
        }
 
        Boolean deleted = entity.getDeleted();
        if (deleted != null) {
            stmt.bindLong(6, deleted ? 1L: 0L);
        }
 
        String name_key = entity.getName_key();
        if (name_key != null) {
            stmt.bindString(7, name_key);
        }
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(8, description);
        }
 
        String uuid = entity.getUuid();
        if (uuid != null) {
            stmt.bindString(9, uuid);
        }
 
        String mac = entity.getMac();
        if (mac != null) {
            stmt.bindString(10, mac);
        }
 
        Integer major = entity.getMajor();
        if (major != null) {
            stmt.bindLong(11, major);
        }
 
        Integer minor = entity.getMinor();
        if (minor != null) {
            stmt.bindLong(12, minor);
        }
 
        Integer rssi = entity.getRssi();
        if (rssi != null) {
            stmt.bindLong(13, rssi);
        }
 
        Integer namespace = entity.getNamespace();
        if (namespace != null) {
            stmt.bindLong(14, namespace);
        }
 
        Integer instance = entity.getInstance();
        if (instance != null) {
            stmt.bindLong(15, instance);
        }
 
        Integer type = entity.getType();
        if (type != null) {
            stmt.bindLong(16, type);
        }
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(17, url);
        }
 
        String message_for_user = entity.getMessage_for_user();
        if (message_for_user != null) {
            stmt.bindString(18, message_for_user);
        }
 
        Boolean is_single = entity.getIs_single();
        if (is_single != null) {
            stmt.bindLong(19, is_single ? 1L: 0L);
        }
        stmt.bindString(20, entity.getLocation_id());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Devices entity) {
        stmt.clearBindings();
        stmt.bindString(1, entity.get_id());
 
        String _etag = entity.get_etag();
        if (_etag != null) {
            stmt.bindString(2, _etag);
        }
 
        java.util.Date created = entity.getCreated();
        if (created != null) {
            stmt.bindLong(3, created.getTime());
        }
 
        java.util.Date updated = entity.getUpdated();
        if (updated != null) {
            stmt.bindLong(4, updated.getTime());
        }
 
        Boolean active = entity.getActive();
        if (active != null) {
            stmt.bindLong(5, active ? 1L: 0L);
        }
 
        Boolean deleted = entity.getDeleted();
        if (deleted != null) {
            stmt.bindLong(6, deleted ? 1L: 0L);
        }
 
        String name_key = entity.getName_key();
        if (name_key != null) {
            stmt.bindString(7, name_key);
        }
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(8, description);
        }
 
        String uuid = entity.getUuid();
        if (uuid != null) {
            stmt.bindString(9, uuid);
        }
 
        String mac = entity.getMac();
        if (mac != null) {
            stmt.bindString(10, mac);
        }
 
        Integer major = entity.getMajor();
        if (major != null) {
            stmt.bindLong(11, major);
        }
 
        Integer minor = entity.getMinor();
        if (minor != null) {
            stmt.bindLong(12, minor);
        }
 
        Integer rssi = entity.getRssi();
        if (rssi != null) {
            stmt.bindLong(13, rssi);
        }
 
        Integer namespace = entity.getNamespace();
        if (namespace != null) {
            stmt.bindLong(14, namespace);
        }
 
        Integer instance = entity.getInstance();
        if (instance != null) {
            stmt.bindLong(15, instance);
        }
 
        Integer type = entity.getType();
        if (type != null) {
            stmt.bindLong(16, type);
        }
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(17, url);
        }
 
        String message_for_user = entity.getMessage_for_user();
        if (message_for_user != null) {
            stmt.bindString(18, message_for_user);
        }
 
        Boolean is_single = entity.getIs_single();
        if (is_single != null) {
            stmt.bindLong(19, is_single ? 1L: 0L);
        }
        stmt.bindString(20, entity.getLocation_id());
    }

    @Override
    protected final void attachEntity(Devices entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.getString(offset + 0);
    }    

    @Override
    public Devices readEntity(Cursor cursor, int offset) {
        Devices entity = new Devices( //
            cursor.getString(offset + 0), // _id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // _etag
            cursor.isNull(offset + 2) ? null : new java.util.Date(cursor.getLong(offset + 2)), // created
            cursor.isNull(offset + 3) ? null : new java.util.Date(cursor.getLong(offset + 3)), // updated
            cursor.isNull(offset + 4) ? null : cursor.getShort(offset + 4) != 0, // active
            cursor.isNull(offset + 5) ? null : cursor.getShort(offset + 5) != 0, // deleted
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // name_key
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // description
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // uuid
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // mac
            cursor.isNull(offset + 10) ? null : cursor.getInt(offset + 10), // major
            cursor.isNull(offset + 11) ? null : cursor.getInt(offset + 11), // minor
            cursor.isNull(offset + 12) ? null : cursor.getInt(offset + 12), // rssi
            cursor.isNull(offset + 13) ? null : cursor.getInt(offset + 13), // namespace
            cursor.isNull(offset + 14) ? null : cursor.getInt(offset + 14), // instance
            cursor.isNull(offset + 15) ? null : cursor.getInt(offset + 15), // type
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // url
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // message_for_user
            cursor.isNull(offset + 18) ? null : cursor.getShort(offset + 18) != 0, // is_single
            cursor.getString(offset + 19) // location_id
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Devices entity, int offset) {
        entity.set_id(cursor.getString(offset + 0));
        entity.set_etag(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCreated(cursor.isNull(offset + 2) ? null : new java.util.Date(cursor.getLong(offset + 2)));
        entity.setUpdated(cursor.isNull(offset + 3) ? null : new java.util.Date(cursor.getLong(offset + 3)));
        entity.setActive(cursor.isNull(offset + 4) ? null : cursor.getShort(offset + 4) != 0);
        entity.setDeleted(cursor.isNull(offset + 5) ? null : cursor.getShort(offset + 5) != 0);
        entity.setName_key(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setDescription(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setUuid(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setMac(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setMajor(cursor.isNull(offset + 10) ? null : cursor.getInt(offset + 10));
        entity.setMinor(cursor.isNull(offset + 11) ? null : cursor.getInt(offset + 11));
        entity.setRssi(cursor.isNull(offset + 12) ? null : cursor.getInt(offset + 12));
        entity.setNamespace(cursor.isNull(offset + 13) ? null : cursor.getInt(offset + 13));
        entity.setInstance(cursor.isNull(offset + 14) ? null : cursor.getInt(offset + 14));
        entity.setType(cursor.isNull(offset + 15) ? null : cursor.getInt(offset + 15));
        entity.setUrl(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setMessage_for_user(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setIs_single(cursor.isNull(offset + 18) ? null : cursor.getShort(offset + 18) != 0);
        entity.setLocation_id(cursor.getString(offset + 19));
     }
    
    @Override
    protected final String updateKeyAfterInsert(Devices entity, long rowId) {
        return entity.get_id();
    }
    
    @Override
    public String getKey(Devices entity) {
        if(entity != null) {
            return entity.get_id();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Devices entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "theLocationDevice" to-many relationship of Location. */
    public List<Devices> _queryLocation_TheLocationDevice(String location_id) {
        synchronized (this) {
            if (location_TheLocationDeviceQuery == null) {
                QueryBuilder<Devices> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.Location_id.eq(null));
                location_TheLocationDeviceQuery = queryBuilder.build();
            }
        }
        Query<Devices> query = location_TheLocationDeviceQuery.forCurrentThread();
        query.setParameter(0, location_id);
        return query.list();
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getLocationDao().getAllColumns());
            builder.append(" FROM DEVICES T");
            builder.append(" LEFT JOIN LOCATION T0 ON T.\"LOCATION_ID\"=T0.\"_ID\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected Devices loadCurrentDeep(Cursor cursor, boolean lock) {
        Devices entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Location theDevices = loadCurrentOther(daoSession.getLocationDao(), cursor, offset);
         if(theDevices != null) {
            entity.setTheDevices(theDevices);
        }

        return entity;    
    }

    public Devices loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<Devices> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<Devices> list = new ArrayList<Devices>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<Devices> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<Devices> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}