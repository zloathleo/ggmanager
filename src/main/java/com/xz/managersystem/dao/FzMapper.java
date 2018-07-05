package com.xz.managersystem.dao;

import com.xz.managersystem.entity.TFzxx;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface FzMapper extends Mapper<TFzxx>, MySqlMapper<TFzxx> {

    @Select("SELECT COUNT(1) FROM t_fz WHERE is_deleted = 0")
    int getVisibleCount();

    @Select("SELECT COUNT(1) FROM t_fz")
    int getAllCount();

    @Select("SELECT \n" +
            "  t.id,\n" +
            "  t.label,\n" +
            "  t.des,\n" +
            "  t.ym_id,\n" +
            "  t.create_time,\n" +
            "  t.update_time,\n" +
            "  b.label as ym_label \n" +
            "  FROM t_fz t " +
            "  LEFT JOIN t_ym b on t.ym_id = b.id " +
            "  WHERE t.is_deleted = 0 " +
            "  ORDER BY t.create_time DESC")
    List<TFzxx> selectVisibleAll();

    @Select("SELECT \n" +
            "  t.id,\n" +
            "  t.label,\n" +
            "  t.des,\n" +
            "  t.ym_id,\n" +
            "  t.create_time,\n" +
            "  t.update_time,\n" +
            "  b.label as ym_label \n" +
            "  FROM t_fz t " +
            "  LEFT JOIN t_ym b on t.ym_id = b.id " +
            "  WHERE t.is_deleted = 0 " +
            "  ORDER BY t.create_time DESC LIMIT #{start}, #{rows}")
    List<TFzxx> selectPage(TablePageParams params);

    @Select("SELECT \n" +
            "  t.id,\n" +
            "  t.label,\n" +
            "  t.des,\n" +
            "  t.ym_id,\n" +
            "  t.create_time,\n" +
            "  t.update_time,\n" +
            "  b.label as ym_label \n" +
            "  FROM t_fz t " +
            "  LEFT JOIN t_fz b on t.ym_id = b.id " +
            "  WHERE t.is_deleted = 0 AND t.id = #{id} LIMIT 1")
    TFzxx findOne(Integer id);

    @Select("SELECT \n" +
            "  t.id,\n" +
            "  t.label,\n" +
            "  t.des,\n" +
            "  t.ym_id,\n" +
            "  t.create_time,\n" +
            "  t.update_time,\n" +
            "  b.label as ym_label \n" +
            "  FROM t_fz t " +
            "  LEFT JOIN t_fz b on t.ym_id = b.id " +
            "  WHERE t.is_deleted = 0 AND t.label = #{label} LIMIT 1")
    TFzxx findOneByName(String label);

    @Insert("INSERT INTO t_fz(label, des, ym_id) VALUES(#{label}, #{des}, #{ymId})")
    int insert(TFzxx fzxx);

    @Update("update t_fz t set t.des = #{des}, t.ym_id = #{ymId} where t.id = #{id}")
    int updateByPrimaryKeySelective(TFzxx fzxx);

    @Delete("update t_fz t set t.is_deleted = 1 where t.id = #{id}")
    int deleteById(Integer id);

    @Delete("update t_fz t set t.is_deleted = 1 where t.label = #{label}")
    int deleteByName(String label);

}
