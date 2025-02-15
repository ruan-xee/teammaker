package com.sen.springboot.mapper;

import com.sen.springboot.model.RoleMapPermission;
import com.sen.springboot.model.RoleMapPermissionExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

public interface RoleMapPermissionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_role_permission
     *
     * @mbg.generated Fri Dec 17 11:07:15 CST 2021
     */
    long countByExample(RoleMapPermissionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_role_permission
     *
     * @mbg.generated Fri Dec 17 11:07:15 CST 2021
     */
    int deleteByExample(RoleMapPermissionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_role_permission
     *
     * @mbg.generated Fri Dec 17 11:07:15 CST 2021
     */
    @Delete({
        "delete from tb_role_permission",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_role_permission
     *
     * @mbg.generated Fri Dec 17 11:07:15 CST 2021
     */
    @Insert({
        "insert into tb_role_permission (id, role_id, ",
        "permission_id, create_time, ",
        "update_time)",
        "values (#{id,jdbcType=BIGINT}, #{roleId,jdbcType=BIGINT}, ",
        "#{permissionId,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP})"
    })
    int insert(RoleMapPermission record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_role_permission
     *
     * @mbg.generated Fri Dec 17 11:07:15 CST 2021
     */
    int insertSelective(RoleMapPermission record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_role_permission
     *
     * @mbg.generated Fri Dec 17 11:07:15 CST 2021
     */
    List<RoleMapPermission> selectByExampleWithRowbounds(RoleMapPermissionExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_role_permission
     *
     * @mbg.generated Fri Dec 17 11:07:15 CST 2021
     */
    List<RoleMapPermission> selectByExample(RoleMapPermissionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_role_permission
     *
     * @mbg.generated Fri Dec 17 11:07:15 CST 2021
     */
    @Select({
        "select",
        "id, role_id, permission_id, create_time, update_time",
        "from tb_role_permission",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("com.sen.springboot.mapper.RoleMapPermissionMapper.BaseResultMap")
    RoleMapPermission selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_role_permission
     *
     * @mbg.generated Fri Dec 17 11:07:15 CST 2021
     */
    int updateByExampleSelective(@Param("record") RoleMapPermission record, @Param("example") RoleMapPermissionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_role_permission
     *
     * @mbg.generated Fri Dec 17 11:07:15 CST 2021
     */
    int updateByExample(@Param("record") RoleMapPermission record, @Param("example") RoleMapPermissionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_role_permission
     *
     * @mbg.generated Fri Dec 17 11:07:15 CST 2021
     */
    int updateByPrimaryKeySelective(RoleMapPermission record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_role_permission
     *
     * @mbg.generated Fri Dec 17 11:07:15 CST 2021
     */
    @Update({
        "update tb_role_permission",
        "set role_id = #{roleId,jdbcType=BIGINT},",
          "permission_id = #{permissionId,jdbcType=BIGINT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(RoleMapPermission record);
}