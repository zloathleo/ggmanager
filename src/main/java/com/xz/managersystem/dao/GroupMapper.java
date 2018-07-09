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

    @Select("SELECT * from t_group WHERE is_deleted = 0 ORDER BY t.create_time DESC LIMIT #{start}, #{rows}")
    List<TGroupInfo> selectPage(TablePageParams params);

    @Select("SELECT * from t_group WHERE is_deleted = 0 AND id = #{id} LIMIT 1")
    TGroupInfo selectById(Integer id);

    @Select("SELECT * FROM t_group WHERE is_deleted = 0 AND label = #{label} LIMIT 1")
    TGroupInfo selectByName(String label);

    @Insert("INSERT INTO t_group(label, des, width, height, page_id, user_id) " +
            "VALUES(#{label}, #{des}, #{width}, #{height}, #{pageId}, #{userId})")
    int insert(TGroupInfo userInfo);

    @Update("update t_group des = #{des}, width = #{width}, height = #{height}, " +
            "page_id = #{pageId}, user_id = #{userId}, update_time = NOW() where id = #{id}")
    int updateById(TGroupInfo userInfo);

    @Update("update t_group des = #{des}, width = #{width}, height = #{height}, " +
            "page_id = #{pageId}, user_id = #{userId}, update_time = NOW() where label = #{label}")
    int updateByName(TGroupInfo userInfo);

    @Delete("update t_group t set is_deleted = 1 where id = #{id}")
    int deleteById(Integer id);

    @Delete("update t_group t set is_deleted = 1 where label = #{label}")
    int deleteByName(String label);

}
