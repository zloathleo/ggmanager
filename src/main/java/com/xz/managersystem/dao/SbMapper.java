package com.xz.managersystem.dao;

import com.xz.managersystem.entity.TSbxx;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface SbMapper extends Mapper<TSbxx>, MySqlMapper<TSbxx> {

    @Select("SELECT COUNT(1) FROM t_sb WHERE is_deleted = 0")
    int getVisibleCount();

    @Select("SELECT COUNT(1) FROM t_sb")
    int getAllCount();

    @Select("SELECT \n" +
            "  t.id,\n" +
            "  t.label,\n" +
            "  t.des,\n" +
            "  t.location,\n" +
            "  t.fz_label,\n" +
            "  t.ym_label, \n" +
            "  t.create_time,\n" +
            "  t.update_time\n" +
            "  FROM t_sb t WHERE t.is_deleted = 0 " +
            "  ORDER BY t.create_time DESC")
    List<TSbxx> selectVisibleAll();

    @Select("SELECT \n" +
            "  t.id,\n" +
            "  t.label,\n" +
            "  t.des,\n" +
            "  t.location,\n" +
            "  t.fz_label,\n" +
            "  t.ym_label, \n" +
            "  t.create_time,\n" +
            "  t.update_time\n" +
            "  FROM t_sb t WHERE t.is_deleted = 0 " +
            "  ORDER BY t.create_time DESC LIMIT #{start}, #{rows}")
    List<TSbxx> selectPage(TablePageParams params);

    @Select("SELECT \n" +
            "  t.id,\n" +
            "  t.label,\n" +
            "  t.des,\n" +
            "  t.location,\n" +
            "  t.fz_label,\n" +
            "  t.ym_label, \n" +
            "  t.create_time,\n" +
            "  t.update_time\n" +
            "  FROM t_sb t WHERE t.is_deleted = 0" +
            "  AND t.label = #{label} LIMIT 1")
    TSbxx selectByName(String label);

    @Insert("INSERT INTO t_sb(label, des, location, fz_label, ym_label) " +
            "VALUES(#{label}, #{des}, #{location}, #{fzLabel}, #{ymLabel})")
    int insert(TSbxx sbxx);

    @Update("UPDATE t_sb t SET t.des = #{des}, t.location = #{location}, t.fz_label = #{fzLabel}, " +
            "t.ym_label = #{ymLabel}, t.update_time=NOW() WHERE t.label = #{label} AND is_deleted = 0")
    int updateByName(TSbxx sbxx);

    @Delete("update t_sb t set t.is_deleted = 1 where t.label = #{label}")
    int deleteByName(String label);

    @Update("UPDATE t_sb t SET t.ym_label = #{arg0} WHERE t.label in (#{arg1})")
    int publishByName(String ymLabel, String sbList);
}
