package com.xz.managersystem.dao;

import com.xz.managersystem.entity.TPageInfo;
import com.xz.managersystem.entity.TResourceInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface ResourceMapper extends Mapper<TResourceInfo>, MySqlMapper<TResourceInfo> {

    @Select("SELECT COUNT(1) FROM t_resource WHERE is_deleted = 0 AND `group` = #{group}")
    int getCount(String group);

    @Select("SELECT COUNT(1) FROM t_resource WHERE is_deleted = 0 AND `group` = #{group} AND type = #{type}")
    int getTypeCount(ConditionParams params);

    @Select("SELECT * from t_resource WHERE is_deleted = 0 AND `group` = #{group} ORDER BY create_time DESC")
    List<TResourceInfo> selectList(String group);

    @Select("SELECT * from t_resource WHERE is_deleted = 0 AND `group` = #{group} " +
            "AND type = #{type} ORDER BY create_time DESC")
    List<TResourceInfo> selectTypeList(ConditionParams params);

    @Select("SELECT * from t_resource WHERE is_deleted = 0 AND `group` = #{group} " +
            "AND type <> 'qr' ORDER BY create_time DESC")
    List<TResourceInfo> selectMediaList(String group);

    @Select("SELECT * from t_resource WHERE is_deleted = 0 AND `group` = #{group} " +
            "AND (type = 'qr' OR type = 'img') ORDER BY create_time DESC")
    List<TResourceInfo> selectPicList(String group);

    @Select("SELECT * from t_resource WHERE is_deleted = 0 AND `group` = #{group} " +
            "ORDER BY create_time DESC LIMIT #{start}, #{rows}")
    List<TResourceInfo> selectPage(ConditionParams params);

    @Select("SELECT * from t_resource WHERE is_deleted = 0 AND `group` = #{group} " +
            "AND type = #{type} ORDER BY create_time DESC LIMIT #{start}, #{rows}")
    List<TResourceInfo> selectTypePage(ConditionParams params);

    @Select("SELECT * from t_resource WHERE is_deleted = 0 AND `group` = #{group} " +
            "AND type <> 'qr' ORDER BY create_time DESC LIMIT #{start}, #{rows}")
    List<TResourceInfo> selectMediaPage(ConditionParams params);

    @Select("SELECT * from t_resource WHERE is_deleted = 0 AND `group` = #{group} " +
            "AND (type = 'qr' OR type = 'img') ORDER BY create_time DESC LIMIT #{start}, #{rows}")
    List<TResourceInfo> selectPicPage(ConditionParams params);

    @Select("SELECT * from t_resource WHERE is_deleted = 0 AND id = #{id} LIMIT 1")
    TResourceInfo selectById(Integer id);

    @Select("SELECT * FROM t_resource WHERE is_deleted = 0 AND label = #{label} LIMIT 1")
    TResourceInfo selectByLabel(String label);

    @Insert("INSERT INTO t_resource(label, name, thumbnail, type, des, size, width, height, `group`) " +
            "VALUES(#{label}, #{name}, #{thumbnail}, #{type}, #{des}, #{size}, #{width}, #{height}, #{group})")
    int insert(TResourceInfo resInfo);

    @Update("UPDATE t_resource SET des = #{des}, update_time = NOW() WHERE id = #{id}")
    int updateById(TResourceInfo resInfo);

    @Update("UPDATE t_resource SET des = #{des}, update_time = NOW() WHERE label = #{label}")
    int updateByLabel(TResourceInfo resInfo);

    @Delete("UPDATE t_resource SET is_deleted = 1 WHERE id = #{id}")
    int deleteById(Integer id);

    @Delete("UPDATE t_resource SET is_deleted = 1 WHERE label = #{label}")
    int deleteByLabel(String label);

}
