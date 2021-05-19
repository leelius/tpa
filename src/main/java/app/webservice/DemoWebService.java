package app.webservice;

import java.util.Iterator;

import org.apache.axiom.om.OMElement;
import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


/***
 * 服务器端
 * 
 * @author Denny
 *
 */
public class DemoWebService {



	/**
	 * 计算減法 d1-d2
	 * 
	 * @throws AxisFault
	 */
	public Integer subtraction(Integer d1, Integer d2) throws AxisFault {
		if (checkLoginInfoInAxis2()) {
			return d1 - d2;
		} else {
			return 0;
		}
	}

	public Integer countUser() throws AxisFault {

		if (checkLoginInfoInAxis2()) {
			return 0;//svc.countVuser(null);
		} else {
			return 0;
		}
	}

	public Boolean checkLoginInfoInAxis2() throws AxisFault {
		MessageContext msgContext = MessageContext.getCurrentMessageContext();
		// 获取Head
		Iterator list = (Iterator) msgContext.getEnvelope().getHeader().getFirstElement().getChildren();
		String username = "";
		String password = "";
		while (list.hasNext()) {
			OMElement element = (OMElement) list.next();
			if (element.getLocalName().equals("username")) {
				username = element.getText();
			}
			if (element.getLocalName().equals("password")) {
				password = element.getText();
			}
		}
		System.out.println("DemoWebService.checkUserPwd():" + "username=" + username + ",password=" + password);
		if (!username.equals("tom") || !password.equals("123456")) {
			return false;
			// throw new AxisFault(" Authentication Fail! Check username/password ");
		} else {
			return true;
		}

	}

}
