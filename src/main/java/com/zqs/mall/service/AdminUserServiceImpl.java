package com.zqs.mall.service;

import com.zqs.mall.dao.AdminUserDao;
import com.zqs.mall.model.User;
import com.zqs.mall.model.vo.AllUserVO;
import com.zqs.mall.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: z_qingshan
 * @create: 2021-05-15
 **/
public class AdminUserServiceImpl implements AdminUserService {

    /**
     * 获取所有用户信息
     *
     * @param user
     * @return
     */
    @Override
    public List<AllUserVO> queryAllUser(User user) {
        SqlSession sqlSession = MyBatisUtils.openSession();
        AdminUserDao mapper = sqlSession.getMapper(AdminUserDao.class);
        List<User> userList = mapper.queryAllUser(user);
        ArrayList<AllUserVO> allUserVOS = new ArrayList<>();
        for (User u : userList) {
            AllUserVO allUserVO = new AllUserVO(u.getId(), u.getEmail(), u.getNickname(), u.getPassword(), u.getReceiver(), u.getAddress(), u.getTelephone());
            allUserVOS.add(allUserVO);
        }
        sqlSession.commit();
        MyBatisUtils.closeSession(sqlSession);
        return allUserVOS;
    }

    /**
     * 通过id删除用户账号
     *
     * @param id
     * @return
     */
    @Override
    public int deleteUserById(Integer id) {
        SqlSession sqlSession = MyBatisUtils.openSession();
        AdminUserDao mapper = sqlSession.getMapper(AdminUserDao.class);
        int code = mapper.deleteUserById(id);
        sqlSession.commit();
        MyBatisUtils.closeSession(sqlSession);
        if (code == 0) {
            // 删除失败
            return 404;
        }
        // 删除成功
        return 200;
    }

    /**
     * 通过用户昵称模糊搜索
     *
     * @param nickName
     * @return
     */
    @Override
    public List<AllUserVO> queryUserByNickName(String nickName) {
        SqlSession sqlSession = MyBatisUtils.openSession();
        AdminUserDao mapper = sqlSession.getMapper(AdminUserDao.class);
        User userNickName = new User(null, null, nickName, null, null, null, null);
        List<User> userList = mapper.queryAllUser(userNickName);
        ArrayList<AllUserVO> allUserVOS = new ArrayList<>();
        for (User user : userList) {
            AllUserVO allUserVO = new AllUserVO(user.getId(), user.getEmail(), user.getNickname(), user.getPassword(), user.getReceiver(), user.getAddress(), user.getTelephone());
            allUserVOS.add(allUserVO);
        }
        sqlSession.commit();
        MyBatisUtils.closeSession(sqlSession);
        return allUserVOS;
    }
}
