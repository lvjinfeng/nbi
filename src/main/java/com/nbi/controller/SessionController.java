package com.nbi.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nbi.pojo.DeliverySession;



@Controller
public class SessionController extends BaseController{
	
	private static Logger logger = LoggerFactory
			.getLogger(SessionController.class);

	@RequestMapping("/startSession")
	public void startSession(HttpServletResponse response) {
		HttpServletRequest request = getRequest();
		HttpSession session = request.getSession();
		DeliverySession deliverySession = null;
		DeliverySession deliverySession2 = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		String info = null;
		if (session.isNew()||session.getAttribute("deliverySession")==null) {
			deliverySession = new DeliverySession();
			deliverySession.setDeliverySessionId(session.getId());
			deliverySession.setCreateTime(new Date(session.getCreationTime()));
			deliverySession.setActionType("start");
			deliverySession.setStartTime(new Date());
			deliverySession.setActionCount(1);
			session.setAttribute("deliverySession", deliverySession);
			info="sessionStartOk, ";
			 info += "sessionId:"+deliverySession.getDeliverySessionId()+", sessionCreateTime:"+sdf.format(deliverySession.getCreateTime())
				+", ActionType:"+deliverySession.getActionType()
				+", sesionStartTime:"+sdf.format(deliverySession.getStartTime())+", total action count:"+deliverySession.getActionCount();
				
			
		}
		else {
			deliverySession = (DeliverySession)session.getAttribute("deliverySession");
			deliverySession2 = new DeliverySession(deliverySession.getDeliverySessionId(), deliverySession.getActionType(), deliverySession.getCreateTime(), new Date(), deliverySession.getStopTime(),
					deliverySession.getActionCount()+1);
			info = "sessionIsOld, ";
			session.removeAttribute("deliverySession");
			session.setAttribute("deliverySession", deliverySession2);
			info += "sessionId:"+deliverySession2.getDeliverySessionId()+", sessionCreateTime:"+sdf.format(deliverySession2.getCreateTime())
			+", ActionType:"+deliverySession2.getActionType()
			+", sesionStartTime:"+sdf.format(deliverySession2.getStartTime())+", total action count:"+deliverySession2.getActionCount();
		}
		logger.info(info);
		
		flushResponse(response,info);
		
	}
	
	@RequestMapping("/stopSession")
	public void stopSession(HttpServletResponse response) {
		HttpServletRequest request = getRequest();
		HttpSession session = request.getSession(false);
		DeliverySession deliverySession = null;
		DeliverySession deliverySession2 = new DeliverySession();
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");	
		String info = null;
		if (session !=null) {
			deliverySession = (DeliverySession)session.getAttribute("deliverySession");
			deliverySession2.setActionType(deliverySession.getActionType());
			deliverySession2.setCreateTime(deliverySession.getCreateTime());
			deliverySession2.setDeliverySessionId(deliverySession.getDeliverySessionId());
			deliverySession2.setStartTime(new Date());
			deliverySession2.setActionCount(deliverySession.getActionCount()+1);
			String sessionId = deliverySession.getDeliverySessionId();
			session.invalidate();
			Date date = new Date();
			deliverySession2.setStopTime(date);
			info=sessionId+" has stopped, stopTime:"+sdf.format(date)+", total action count:"+deliverySession2.getActionCount();
		}
		else {
			info = "Your Session already stopped,please create a new one!";
		}
		
		logger.info(info);
		flushResponse(response, info);
	}
}
