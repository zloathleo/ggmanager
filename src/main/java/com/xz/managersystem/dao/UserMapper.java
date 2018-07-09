package com.xz.managersystem.dao;

import com.xz.managersystem.entity.TUserInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface UserMapper extends Mapper<TUserInfo>, MySqlMapper<TUserInfo> {

    @Select("SELECT COUNT(1) FROM t_user WHERE is_deleted = 0")
    int getCount();

    @Select("SELECT COUNT(1) FROM t_user WHERE is_deleted = 0 AND type = #{type}")
    int getTypeCount(String type);

    @Select("SELECT * from t_user WHERE is_deleted = 0 ORDER BY create_time DESC")
    List<TUserInfo> selectList();

    @Select("SELECT * from t_user WHERE is_deleted = 0 ORDER BY t.create_time DESC LIMIT #{start}, #{rows}")
    List<TUserInfo> selectPage(TablePageParams params);

    @Select("SELECT * from t_user WHERE is_deleted = 0 AND type = #{type} " +
            "ORDER BY t.create_time DESC LIMIT #{start}, #{rows}")
    List<TUserInfo> selectTypePage(TablePageParams params);

    @Select("SELECT * from t_user WHERE is_deleted = 0 AND id = #{id} LIMIT 1")
    TUserInfo selectById(Integer id);

    @Select("SELECT * FROM t_user WHERE is_deleted = 0 AND user = #{user} LIMIT 1")
    TUserInfo selectByName(String label);

    @Insert("INSERT INTO t_user(user, password, page_id) VALUES(#{label}, #{password}, #{pageId})")
    int insert(TUserInfo userInfo);

    @Update("update t_user password = #{password}, page_id = #{pageId}, update_time = NOW() where id = #{id}")
    int updateById(TUserInfo userInfo);

    @Update("update t_user password = #{password}, page_id = #{pageId}, update_time = NOW() where user = #{user}")
    int updateByName(TUserInfo userInfo);

    @Delete("update t_user t set is_deleted = 1 where id = #{id}")
    int deleteById(Integer id);

    @Delete("update t_user t set is_deleted = 1 where user = #{user}")
    int deleteByName(String label);

}
