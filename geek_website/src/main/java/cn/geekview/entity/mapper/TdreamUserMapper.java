package cn.geekview.entity.mapper;

import cn.geekview.entity.model.TdreamUser;
import org.apache.ibatis.annotations.Insert;

public interface TdreamUserMapper {

    String insert = "insert into t_dream_user (pk_id, email, nick_name, \n" +
            "      password, openid, gender, \n" +
            "      register_time, user_status, remark\n" +
            "      )\n" +
            "    values (#{pkId,jdbcType=INTEGER}, #{email,jdbcType=VARCHAR}, #{nickName,jdbcType=VARCHAR}, \n" +
            "      #{password,jdbcType=VARCHAR}, #{openid,jdbcType=VARCHAR}, #{gender,jdbcType=INTEGER}, \n" +
            "      #{registerTime,jdbcType=TIMESTAMP}, #{userStatus,jdbcType=INTEGER}, #{remark,jdbcType=VARCHAR}\n" +
            "      )";
    @Insert(insert)
    int insert(TdreamUser user);
}
