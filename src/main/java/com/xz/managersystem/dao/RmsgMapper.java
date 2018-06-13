package com.xz.managersystem.dao;

import com.xz.managersystem.entity.TRmsg;
import org.apache.ibatis.annotations.Insert;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface RmsgMapper extends Mapper<TRmsg>, MySqlMapper<TRmsg> {

    @Insert("INSERT INTO t_rmsg (label,ggym_id,content) VALUES ( #{label},#{ggymId},#{content} )")
    int insert(TRmsg ont);

}
