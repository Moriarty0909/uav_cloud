package com.ccssoft.clouduav.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccssoft.cloudcommon.common.utils.R;
import com.ccssoft.cloudcommon.entity.Uav;
import com.ccssoft.clouduav.entity.UserUav;
import com.ccssoft.clouduav.filter.RedisBloomFilter;
import com.ccssoft.clouduav.service.UavService;
import com.ccssoft.clouduav.service.UserUavService;
import com.ccssoft.clouduav.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 无人机设备信息 前端控制器
 * </p>
 *
 * @author moriarty
 * @since 2020-05-28
 */
@RestController
@RequestMapping("/uav")
@Slf4j
public class UavController {
    @Resource
    private UavService uavService;

    @Resource
    private UserUavService userUavService;

    @Resource
    private RedisBloomFilter bloomFilter;

    @Resource
    private RedisUtil redisUtil;

    /**
     * 注册无人机
     * @param uav 无人机详情
     * @return 是否成功
     */
    @PostMapping("/register")
    public R registerUav(@Valid @RequestBody Uav uav) {
        log.info("UavController.registerUav(),参数：uav={}",uav);
        return uavService.saveUav(uav,uav.getUserId()) == 1 ? R.ok() : R.error(300,"注册失败!");
    }

    /**
     * 更新信息
     * @param uav 无人机信息
     * @return 成功与否
     */
    @PostMapping("/updateInfo")
    public R updateInfo (@Valid @RequestBody Uav uav) {
        log.info("UavController.updateInfo(),参数：uav={}",uav);
        return uavService.updateById(uav) ? R.ok() : R.error(300,"修改失败！");
    }

    /**
     * 获取所有在册无人机分页数据
     * @param current 当前页
     * @param size 每页数据量
     * @return 分页数据
     */
    @GetMapping("/getUav4Page/{current}&{size}")
    public R getUav4Page (@PathVariable("current") int current, @PathVariable("size") int size) {
        log.info("UavController.getUav4Page(),参数:当前第{}页,一页{}个",current,size);
        return R.ok(uavService.getUav4Page(current,size));
    }

    /**
     * 根据用户id来获取其本身的无人机列表分页数据
     * @param current 当前页
     * @param size 每页数据量
     * @param userId 用户id
     * @return 分页数据
     */
    @GetMapping("/getUavByUserId4Page/{current}&{size}&{id}")
    public R getUavByUserId4Page (@PathVariable("current") int current, @PathVariable("size") int size ,@PathVariable("id") Long userId) {
        log.info("UavController.getUavByUserId4Page(),参数:当前第{}页,一页{}个,用户id={}",current,size,userId);
        Page uavByUserId4Page = uavService.getUavByUserId4Page(current, size, userId);
        return uavByUserId4Page != null ?R.ok(uavByUserId4Page) : R.error(301,"查询不到无人机！");
    }

    /**
     * 删除无人机信息
     * @param uavId 无人机id
     * @return 是否成功
     */
    @GetMapping("/deleteUavById/{id}")
    public R deleteUavById (@PathVariable("id") Long uavId) {
        log.info("UavController.deleteUavById(),参数:无人机id={}",uavId);
        return uavService.removeById(uavId) ? R.ok() : R.error(300,"删除失败！");
    }

    @GetMapping("/getUavCount")
    public R getUavCount () {
        log.info("UavController.getUavCount()");
        return R.ok(uavService.getUavCount());
    }

    /**
     * 根据UserId来获取对应的关系列表中的Uavs
     * @param userId 用户id
     * @return 一组uav数据
     */
    @GetMapping("/getUavsByUserId")
    public List getUavsByUserId (@RequestParam("userId") Long userId) {
        log.info("UavController.getUavsByUserId(),参数:userId={}",userId);
        String numId = String.valueOf(userId);
        if (!bloomFilter.isExist(numId)) {
            return null;
        }

        if (redisUtil.get("uavBy"+numId) == null) {
            QueryWrapper<UserUav> wrapper = new QueryWrapper();
            wrapper.eq("user_id",userId);
            List<Uav> list = new ArrayList();
            for (UserUav userUav : userUavService.list(wrapper)) {
                QueryWrapper<Uav> uavQueryWrapper = new QueryWrapper<>();
                uavQueryWrapper.eq("id",userUav.getUavId());
                list.add(uavService.getOne(uavQueryWrapper));
            }
            redisUtil.set("uavBy"+numId,list);
            return list;
        }

        return JSONUtil.parseArray(redisUtil.get("uavBy"+numId));
    }

    /**
     * 根据UserId来获取对应的关系列表中的UavId，供远程服务调用
     * @param userId 用户id
     * @return 一组uavId数据
     */
    @GetMapping("/getUavByUserId")
    public List<Long> getUavIdByUserId (@RequestParam("userId") Long userId) {
        log.info("UavController.getUavByUserId(),参数:userId={}",userId);
        String numId = String.valueOf(userId);
        if (!bloomFilter.isExist(numId)) {
           return null;
        }
        if (redisUtil.get(numId) == null) {
            QueryWrapper<UserUav> wrapper = new QueryWrapper();
            wrapper.eq("user_id",userId);
            List<Long> list = new ArrayList();
            for (UserUav userUav : userUavService.list(wrapper)) {
                list.add(userUav.getUavId());
            }
            redisUtil.set(numId,list);
            return list;
        }

        return JSONUtil.parse(redisUtil.get(numId)).toBean(List.class);

    }

    /**
     * 可以让task模块远程调用，通过无人机id获取相对应的无人机详情
     * @param uavId 无人机id
     * @return 无人机详情
     */
    @GetMapping("/getUavById/{id}")
    public Uav getUavById (@PathVariable("id") Long uavId) {
        log.info("UavController.getUavById(),参数:uavId={}",uavId);
        String numId = String.valueOf(uavId);
        if (!bloomFilter.isExist(numId)) {
            return null;
        }

        if (redisUtil.get(numId) == null) {
            Uav uav = uavService.getById(uavId);
            redisUtil.set(numId,uav);
            return uav;
        }

        return JSONUtil.parse(redisUtil.get(numId)).toBean(Uav.class);
    }
}

