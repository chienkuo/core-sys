package com.fjykt.mapper;

import com.fjykt.domain.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by pc on 2017/4/10.
 */
public interface HelloMapper {
    @Select("SELECT * FROM users WHERE id = #{id}")
    User getUser(@Param("id") String userId);
}
