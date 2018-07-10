package com.xz.managersystem.dao;

import com.xz.managersystem.entity.TGroupInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface GroupMapper extends Mapper<TGroupInfo>, MySqlMapper<TGroupInfo> {

    @Select("SELECT COUNT(1) FROM t_group WHERE is_deleted = 0")
    int getCount();

    @Select("SELECT * from t_group WHERE is_deleted = 0 ORDER BY create_time DESC")
    List<TGroupInfo> selectList();

    @Select("SELECT * from t_group WHERE is_deleted = 0 ORDER BY create_time DESC LIMIT #{start}, #{rows}")
    List<TGroupInfo> selectPage(ConditionParams params);

    @Select("SELECT * from t_group WHERE is_deleted = 0 AND id = #{id} LIMIT 1")
    TGroupInfo selectById(Integer id);

    @Select("SELECT * FROM t_group WHERE is_deleted = 0 AND label = #{label} LIMIT 1")
    TGroupInfo selectByLabel(String label);

    @Insert("INSERT INTO t_group(label, name, width, height, type, page, user) " +
            "VALUES(#{label}, #{name}, #{width}, #{height}, #{type}, #{page}, #{user})")
    int insert(TGroupInfo userInfo);

    @Update("UPDATE t_group SET name = #{name}, width = #{width}, height = #{height}, type = #{type}, " +
            "page = #{page}, user = #{user}, update_time = NOW() WHERE id = #{id}")
    int updateById(TGroupInfo userInfo);

    @Update("UPDATE t_group SET name = #{name}, width = #{width}, height = #{height}, type = #{type}, " +
            "page = #{page}, user = #{user}, update_time = NOW() WHERE label = #{label}")
    int updateByLabel(TGroupInfo userInfo);

    @Delete("UPDATE t_group SET is_deleted = 1 WHERE id = #{id}")
    int deleteById(Integer id);

    @Delete("UPDATE t_group SET is_deleted = 1 WHERE label = #{label}")
    int deleteByLabel(String label);

}
