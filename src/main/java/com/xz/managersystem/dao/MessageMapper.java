package com.xz.managersystem.dao;

import com.xz.managersystem.entity.TMessageInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface MessageMapper extends Mapper<TMessageInfo>, MySqlMapper<TMessageInfo> {

    @Select("SELECT COUNT(1) FROM t_message WHERE is_deleted = 0 AND `group` = #{group}")
    int getCount(String group);

    @Select("SELECT COUNT(1) FROM t_message WHERE is_deleted = 0 AND `group` = #{group} " +
            "AND NOW() BETWEEN start_time AND end_time")
    int getFilterCount(String group);

    @Select("SELECT * from t_message WHERE is_deleted = 0 AND `group` = #{group} ORDER BY create_time DESC")
    List<TMessageInfo> selectList(String group);

    @Select("SELECT * from t_message WHERE is_deleted = 0 AND `group` = #{group} " +
            "AND NOW() BETWEEN start_time AND end_time ORDER BY create_time DESC")
    List<TMessageInfo> selectFilterList(String group);

    @Select("SELECT * from t_message WHERE is_deleted = 0 AND `group` = #{group} " +
            "ORDER BY create_time DESC LIMIT #{start}, #{rows}")
    List<TMessageInfo> selectPage(ConditionParams params);

    @Select("SELECT * from t_message WHERE is_deleted = 0 AND `group` = #{group} " +
            "AND NOW() BETWEEN start_time AND end_time ORDER BY create_time DESC LIMIT #{start}, #{rows}")
    List<TMessageInfo> selectFilterPage(ConditionParams params);

    @Select("SELECT * from t_message WHERE is_deleted = 0 AND id = #{id} LIMIT 1")
    TMessageInfo selectById(Integer id);

    @Select("SELECT * FROM t_message WHERE is_deleted = 0 AND label = #{label} LIMIT 1")
    TMessageInfo selectByLabel(String label);

    @Insert("INSERT INTO t_message(label, name, type, start_time, end_time, `group`) " +
            "VALUES(#{label}, #{name}, #{type}, #{startTime}, #{endTime}, #{group})")
    int insert(TMessageInfo msgInfo);

    @Update("UPDATE t_message SET start_time = #{startTime}, end_time = #{endTime}, " +
            "update_time = NOW() WHERE id = #{id}")
    int updateById(TMessageInfo msgInfo);

    @Update("UPDATE t_message SET start_time = #{startTime}, end_time = #{endTime}, " +
            "update_time = NOW() WHERE label = #{label}")
    int updateByLabel(TMessageInfo msgInfo);

    @Delete("UPDATE t_message SET is_deleted = 1 WHERE id = #{id}")
    int deleteById(Integer id);

    @Delete("UPDATE t_message SET is_deleted = 1 WHERE label = #{label}")
    int deleteByLabel(String label);

}
