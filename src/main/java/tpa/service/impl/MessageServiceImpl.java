//-----------------------------
//-- Generated By Denny(luhox@qq.com)
//-- Remarks: ServiceImpl
//-- Date Generated: 2020-10-09 10:48:00
//-----------------------------

package tpa.service.impl;

import tpa.entity.Message;
import tpa.mapper.MessageMapper;
import tpa.service.IMessageService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import sld.webutils.Util;

import java.util.*;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service("messageService")
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

	@Autowired
	private MessageMapper messageMapper;
	@Override
	public Integer max(String fieldname) {
		return messageMapper.max(fieldname);
	}
	@Override
	public Map<String, Integer> statMapByFieldname(String fieldname) {
		return Util.listmapToMap(messageMapper.statMapByFieldname(fieldname));
	}
	@Override
	public void updatePlus(String fieldname, Integer addend, Integer id) {
		messageMapper.updatePlus(fieldname, addend, id);
	}
}
