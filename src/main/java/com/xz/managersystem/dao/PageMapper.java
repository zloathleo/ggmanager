package com.xz.managersystem.dao;

import com.xz.managersystem.entity.TPageInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface PageMapper extends Mapper<TPageInfo>, MySqlMapper<TPageInfo> {

    @Select("SELECT COUNT(1) FROM t_page WHERE is_deleted = 0 AND `group` = #{group}")
    int getCount(String group);

    @Select("SELECT * from t_page WHERE is_deleted = 0 AND `group` = #{group} ORDER BY create_time DESC")
    List<TPageInfo> selectList(String group);

    @Select("SELECT * from t_page WHERE is_deleted = 0 AND `group` = #{group} " +
            "ORDER BY create_time DESC LIMIT #{start}, #{rows}")
    List<TPageInfo> selectPage(ConditionParams params);

    @Select("SELECT * from t_page WHERE is_deleted = 0 AND id = #{id} LIMIT 1")
    TPageInfo selectById(Integer id);

    @Select("SELECT * FROM t_page WHERE is_deleted = 0 AND label = #{label} LIMIT 1")
    TPageInfo selectByLabel(String label);

    @Insert("INSERT INTO t_page(label, name, content, `group`) " +
            "VALUES(#{label}, #{name}, #{content}, #{group})")
    int insert(TPageInfo pageInfo);

    @Update("UPDATE t_page SET name = #{name}, content = #{content}, update_time = NOW() WHERE id = #{id}")
    int updateById(TPageInfo pageInfo);

    @Update("UPDATE t_page SET name = #{name}, content = #{content}, update_time = NOW() WHERE label = #{label}")
    int updateByLabel(TPageInfo pageInfo);

    @Delete("UPDATE t_page SET is_deleted = 1 WHERE id = #{id}")
    int deleteById(Integer id);

    @Delete("UPDATE t_page SET is_deleted = 1 WHERE label = #{label}")
    int deleteByLabel(String label);

}
