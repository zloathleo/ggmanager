package com.xz.managersystem.dao;

import com.xz.managersystem.entity.TGgym;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface GgymMapper extends Mapper<TGgym>, MySqlMapper<TGgym> {

    @Select("SELECT COUNT(1) FROM t_ggym WHERE is_delete = 0")
    int getVisibleCount();

    @Select("SELECT COUNT(1) FROM t_ggym")
    int getAllCount();

    @Select("SELECT \n" +
            "  t.id,\n" +
            "  t.label,\n" +
            "  t.stype,\n" +
            "  t.des,\n" +
            "  t.video_urls,\n" +
            "  t.img_urls,\n" +
            "  t.text_msg,\n" +
            "  t.create_time,\n" +
            "  t.update_time \n" +
            "  FROM t_ggym t WHERE is_delete = 0 ORDER BY create_time DESC ")
    List<TGgym> selectVisibleAll();

    @Select("SELECT \n" +
            "  t.id,\n" +
            "  t.label,\n" +
            "  t.stype,\n" +
            "  t.des,\n" +
            "  t.video_urls,\n" +
            "  t.img_urls,\n" +
            "  t.text_msg,\n" +
            "  t.create_time,\n" +
            "  t.update_time \n" +
            "  FROM t_ggym t WHERE is_delete = 0 ORDER BY create_time DESC LIMIT #{start},#{rows} ")
    List<TGgym> selectPage(TablePageParams params);

    @Select("SELECT \n" +
            "  t.id,\n" +
            "  t.label,\n" +
            "  t.des,\n" +
            "  t.stype,\n" +
            "  t.video_urls,\n" +
            "  t.img_urls,\n" +
            "  t.text_msg,\n" +
            "  t.create_time,\n" +
            "  t.update_time \n" +
            "  FROM t_ggym t WHERE t.id =#{id} LIMIT 1")
    TGgym findOne(Integer id);

    @Select("SELECT \n" +
            "  t.id,\n" +
            "  t.label,\n" +
            "  t.des,\n" +
            "  t.stype,\n" +
            "  t.video_urls,\n" +
            "  t.img_urls,\n" +
            "  t.create_time,\n" +
            "  t.update_time \n" +
            "  FROM t_ggym t  WHERE t.label =#{label} LIMIT 1")
    TGgym findOneByName(String label);

    @Insert("INSERT INTO t_ggym (label,des,stype, video_urls ,img_urls,text_msg) VALUES (#{label},#{des},#{stype}, #{videoUrls},#{imgUrls},#{textMsg})")
    int insert(TGgym tGgym);

    @Update("update t_ggym t set t.des=#{des},t.stype=#{stype},t.video_urls=#{videoUrls} ,t.img_urls=#{imgUrls} ,t.text_msg=#{textMsg} where t.id=#{id}")
    int updateByPrimaryKeySelective(TGgym tGgym);

    @Delete("update t_ggym t set t.is_delete=1 where t.id=#{id}")
    int deleteById(Integer id);

}
