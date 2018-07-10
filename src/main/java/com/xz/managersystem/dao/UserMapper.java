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

    @Select("SELECT COUNT(1) FROM t_user WHERE is_deleted = 0 AND type = 'operator' AND `group` = ''")
    int getFilterCount();

    @Select("SELECT COUNT(1) FROM t_user WHERE is_deleted = 0 AND type = #{type}")
    int getTypeCount(String type);

    @Select("SELECT * FROM t_user WHERE is_deleted = 0 ORDER BY create_time DESC")
    List<TUserInfo> selectList();

    @Select("SELECT * FROM t_user WHERE is_deleted = 0 AND type = 'operator' AND `group` = '' " +
            "ORDER BY create_time DESC")
    List<TUserInfo> selectFilterList();

    @Select("SELECT * FROM t_user WHERE is_deleted = 0 AND type = #{type} ORDER BY create_time DESC")
    List<TUserInfo> selectTypeList(String type);

    @Select("SELECT * FROM t_user WHERE is_deleted = 0 ORDER BY create_time DESC LIMIT #{start}, #{rows}")
    List<TUserInfo> selectPage(ConditionParams params);

    @Select("SELECT * FROM t_user WHERE is_deleted = 0 AND type = 'operator' AND `group` = '' " +
            "ORDER BY create_time DESC LIMIT #{start}, #{rows}")
    List<TUserInfo> selectFilterPage(ConditionParams params);

    @Select("SELECT * FROM t_user WHERE is_deleted = 0 AND type = #{type} " +
            "ORDER BY create_time DESC LIMIT #{start}, #{rows}")
    List<TUserInfo> selectTypePage(ConditionParams params);

    @Select("SELECT * FROM t_user WHERE is_deleted = 0 AND id = #{id} LIMIT 1")
    TUserInfo selectById(Integer id);

    @Select("SELECT * FROM t_user WHERE is_deleted = 0 AND name = #{name} LIMIT 1")
    TUserInfo selectByName(String label);

    @Insert("INSERT INTO t_user(name, password, type, `group`) VALUES(#{name}, #{password}, #{type}, #{group})")
    int insert(TUserInfo userInfo);

    @Update("UPDATE t_user SET password = #{password}, `group` = #{group}, update_time = NOW() WHERE id = #{id}")
    int updateById(TUserInfo userInfo);

    @Update("UPDATE t_user SET password = #{password}, `group` = #{group}, update_time = NOW() WHERE name = #{name}")
    int updateByName(TUserInfo userInfo);

    @Delete("UPDATE t_user t SET is_deleted = 1 WHERE id = #{id}")
    int deleteById(Integer id);

    @Delete("UPDATE t_user t SET is_deleted = 1 WHERE name = #{name}")
    int deleteByName(String label);

}
