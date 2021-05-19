package app.service.tester;

import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import app.entity.Menu;
import app.service.BrowseService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-context.xml")
public class TestSQL {



	@Transactional
	@Test
	@Rollback(true)
	public void testExecute() throws Exception {

//	SELECT * FROM cmsos.varticle where  
//	Column_id in (SELECT id FROM Column where Column_parentid=1 or id=1)
//	and article_is_actived =1
//	and toptill > 1518560000000
//	order by sortid 
//	limit 6

//		Integer column_id = 1;
//		Integer number = 6;
//		Long toptill= 1518560000000l;
//		
//		List<Column> Columns = svccolumn.list(new QueryWrapper<Column>()
//				.eq(Column.COLUMN_PARENTID, column_id).or().eq(Column.ID, column_id));
//		ArrayList<Integer> columnids = new ArrayList<Integer>();
//		for (Column Column : Columns) {
//			System.out.println("TestSQL.testExecute()" + Column.toString());
//			columnids.add(Column.getId());
//		}
//
//		QueryWrapper<Varticle> qw = new QueryWrapper<Varticle>().in(Varticle.COLUMN_ID, columnids)
//				.eq(Varticle.ARTICLE_IS_ACTIVED, true).gt(Varticle.TOPTILL, new Date().getTime())
//				.orderByDesc(Varticle.SORTID).last("limit " + number);
//
//		List<Varticle> list = svcvarticle.list(qw);
//		
//		for (Varticle varticle : list) {
//			System.out.println("TestSQL.testExecute()varticle=" + varticle.getTitle());
//		}

	}
}
