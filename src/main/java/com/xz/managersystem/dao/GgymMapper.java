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
            "  t.ggmb_id,\n" +
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
            "  t.ggmb_id,\n" +
            "  t.video_urls,\n" +
            "  t.img_urls,\n" +
            "  t.text_msg,\n" +
            "  b.label AS mbLabel,\n" +
            "  b.des AS mbDes\n" +
            "FROM\n" +
            "  (SELECT \n" +
            "    id,\n" +
            "    label,\n" +
            "    stype,\n" +
            "    des,\n" +
            "    ggmb_id,\n" +
            "    video_urls,\n" +
            "    img_urls,\n" +
            "    text_msg,\n" +
            "    create_time,\n" +
            "    update_time \n" +
            "  FROM\n" +
            "    t_ggym \n" +
            "  WHERE is_delete = 0 \n" +
            "  ORDER BY create_time DESC \n" +
            "  LIMIT #{start}, #{rows} ) t \n" +
            "  LEFT JOIN t_ggmb b \n" +
            "    ON t.ggmb_id = b.id \n" +
            "   ORDER BY t.create_time DESC")
    List<TGgym> selectPage(TablePageParams params);

    @Select("SELECT \n" +
            "  t.id,\n" +
            "  t.label,\n" +
            "  t.des,\n" +
            "  t.stype,\n" +
            "  t.ggmb_id,\n" +
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
            "  t.ggmb_id,\n" +
            "  t.video_urls,\n" +
            "  t.img_urls,\n" +
            "  t.create_time,\n" +
            "  t.update_time \n" +
            "  FROM t_ggym t  WHERE t.label =#{label} LIMIT 1")
    TGgym findOneByName(String label);

    @Insert("INSERT INTO t_ggym (label,des,stype, ggmb_id,video_urls ,img_urls,text_msg) VALUES (#{label},#{des},#{stype}, #{ggmbId}, #{videoUrls},#{imgUrls},#{textMsg})")
    int insert(TGgym tGgym);

    @Update("update t_ggym t set t.des=#{des},t.stype=#{stype},t.ggmb_id=#{ggmbId}, t.video_urls=#{videoUrls} ,t.img_urls=#{imgUrls} ,t.text_msg=#{textMsg} where t.id=#{id}")
    int updateByPrimaryKeySelective(TGgym tGgym);

    @Delete("update t_ggym t set t.is_delete=1 where t.id=#{id}")
    int deleteById(Integer id);

}
