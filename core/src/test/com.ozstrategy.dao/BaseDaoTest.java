import com.ozstrategy.dao.menus.MenuDao;
import com.ozstrategy.model.menus.Menu;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lihao1 on 4/30/15.
 */
public class BaseDaoTest extends BaseManagerTestCase{
    @Autowired
    private MenuDao menuDao;
    @Test
    public void testSelect(){
        try {
            List<Menu> list=  menuDao.listAll();
            System.out.println(list.size());
            Map<String,Object> map=new HashMap<String, Object>();
//            map.put("Q_id_EQ",2);
//            map.put("Q_name_LK","%库%");
            list=menuDao.listPage(map,0,1);
            System.out.println(list.size());


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Test
    @Rollback(value = false)
    public void testSave(){
        Menu menu=new Menu();
        menu.setName("asdf");
        menu.setMenuKey("dee");
        menu.setWidget("ewe3");
        Menu newMenu = menuDao.save(menu);
        System.out.println(newMenu.getId());

    }
    @Test
    @Rollback(value = false)
    public void testUpdate(){
        Menu menu=menuDao.get(5L);
        System.out.println(menu.getMenuKey());
        menu.setName("1");
        menu.setWidget("1");
        menu.setMenuKey("1");
        menuDao.update(menu);
        System.out.println(menu.getId());

    }
    @Test
    @Rollback(value = false)
    public void testDelete(){
        Menu menu=menuDao.get(5L);
        menuDao.delete(menu);
        menuDao.deleteById(7L);

        System.out.println(menu.getId());

    }


}
