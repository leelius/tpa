package app.listener;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@WebListener
public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent event) {

      //  System.out.println("SessionListener.sessionCreated()sessionCreated");
        HttpSession session = event.getSession();
        ServletContext context = session.getServletContext();

        Map<String, Long> vmap = (HashMap<String, Long>) context.getAttribute("vmap");

        if (vmap == null) {
            vmap = new HashMap<>();
            context.setAttribute("vmap", vmap);
        }
        //这里主要是为了检验用户是否登录，登录的话强制移除该session，加入新session
        String curjsessionid = session.getId();
        Long curtime = new Date().getTime();
        Boolean isnewvisitor = true;

        Iterator iter = vmap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = entry.getKey().toString();
            Long val = Long.parseLong(entry.getValue().toString());
            System.out.println("key = " + key);
            System.out.println("val = " + val);

            if (curjsessionid.equals(key)) {
                vmap.replace(key, val, curtime);
                isnewvisitor = false;
            }
            break;
        }
        if (isnewvisitor) {
            vmap.put(curjsessionid, curtime);
        }

        //存储在线人数，利用了set集合不重复的特性，避免了重复登录
        context.setAttribute("visitorCount", vmap.size() + "");
       // System.out.println("sessionCreated().visitorCount = " + vmap.size());


    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {

        //System.out.println("SessionListener.sessionDestroyed()sessionDestroyed");
        ServletContext context = event.getSession().getServletContext();
        if (context.getAttribute("visitorCount") == null) {
            context.setAttribute("visitorCount", 0);
        } else {
            int visitorCount = Integer.parseInt(context.getAttribute("visitorCount").toString());
            if (visitorCount < 1) {
                visitorCount = 1;
            }
            context.setAttribute("visitorCount", visitorCount - 1);
        }
        HttpSession session = event.getSession();
        String curjsessionid = session.getId();
        Map<String, Long> vmap = (HashMap<String, Long>) context.getAttribute("vmap");
        try {
        	vmap.remove(curjsessionid);
		} catch (Exception e) {

		}
        

        context.setAttribute("vmap", vmap);
        if (vmap!=null) {
        	 context.setAttribute("visitorCount", vmap.size() + "");
		}else {
			 context.setAttribute("visitorCount", "0");
		}       
        //System.out.println("sessionDestroyed().visitorCount = " + vmap.size());
    }

}
