package com.xz.managersystem.dao;

import com.xz.managersystem.entity.TYmxx;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface YmglMapper extends Mapper<TYmxx>, MySqlMapper<TYmxx> {

    @Select("SELECT COUNT(1) FROM t_ym WHERE is_deleted = 0")
    int getVisibleCount();

    @Select("SELECT COUNT(1) FROM t_ym")
    int getAllCount();

    @Select("SELECT \n" +
            "  t.id,\n" +
            "  t.label,\n" +
            "  t.des,\n" +
            "  t.video_urls,\n" +
            "  t.img_urls,\n" +
            "  t.text_urls,\n" +
            "  t.mb_id,\n" +
            "  t.create_time,\n" +
            "  t.update_time,\n" +
            "  b.label as mb_label \n" +
            "  FROM t_ym t LEFT JOIN t_mb b on t.mb_id = b.id " +
            "  WHERE t.is_deleted = 0 " +
            "  ORDER BY t.create_time DESC ")
    List<TYmxx> selectVisibleAll();

    @Select("SELECT \n" +
            "  t.id,\n" +
            "  t.label,\n" +
            "  t.des,\n" +
            "  t.video_urls,\n" +
            "  t.img_urls,\n" +
            "  t.text_urls,\n" +
            "  t.mb_id,\n" +
            "  t.create_time,\n" +
            "  t.update_time,\n" +
            "  b.label as mb_label \n" +
            "  FROM t_ym t LEFT JOIN t_mb b on t.mb_id = b.id " +
            "  WHERE t.is_deleted = 0 " +
            "  ORDER BY t.create_time DESC " +
            "  LIMIT #{start}, #{rows}")
    List<TYmxx> selectPage(TablePageParams params);

    @Select("SELECT \n" +
            "  t.id,\n" +
            "  t.label,\n" +
            "  t.des,\n" +
            "  t.video_urls,\n" +
            "  t.img_urls,\n" +
            "  t.text_urls,\n" +
            "  t.mb_id,\n" +
            "  t.create_time,\n" +
            "  t.update_time, \n" +
            "  b.label as mb_label \n" +
            "  FROM t_ym t LEFT JOIN t_mb b on t.mb_id = b.id " +
            "  WHERE t.is_deleted = 0 and t.id = #{id} LIMIT 1")
    TYmxx findOne(Integer id);

    @Select("SELECT \n" +
            "  t.id,\n" +
            "  t.label,\n" +
            "  t.des,\n" +
            "  t.video_urls,\n" +
            "  t.img_urls,\n" +
            "  t.text_urls,\n" +
            "  t.mb_id,\n" +
            "  t.create_time,\n" +
            "  t.update_time, \n" +
            "  b.label as mb_label \n" +
            "  FROM t_ym t LEFT JOIN t_mb b on t.mb_id = b.id " +
            "  WHERE t.is_deleted = 0 and t.label = #{label} LIMIT 1")
    TYmxx findOneByName(String label);

    @Insert("INSERT INTO t_ym(label, des, video_urls, img_urls, text_urls, mb_id) VALUES(#{label}, #{des}, #{videoUrls}, #{imgUrls}, #{textUrls}, #{mbId})")
    int insert(TYmxx ymxx);

    @Update("update t_ym t set t.des = #{des}, t.video_urls= #{videoUrls}, t.img_urls = #{imgUrls}, t.text_urls= #{textUrls} where t.id = #{id}")
    int updateByPrimaryKeySelective(TYmxx ymxx);

    @Delete("update t_ym t set t.is_deleted = 1 where t.id = #{id}")
    int deleteById(Integer id);

}
