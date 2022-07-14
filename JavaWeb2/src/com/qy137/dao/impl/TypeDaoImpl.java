package com.qy137.dao.impl;

import com.qy137.dao.TypeDao;
import com.qy137.utils.DBUtil;

import java.util.List;
import java.util.Map;

public class TypeDaoImpl implements TypeDao {
    @Override
    public List<Map> getAllType() {
        String sql="select * from t_type";
        List<Map> map = DBUtil.queryListMap(sql);
        if (map.size()>0){
            return map;
        }
        return null;
    }
}
