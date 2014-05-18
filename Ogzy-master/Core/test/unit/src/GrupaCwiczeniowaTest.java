
import org.database.models.GrupaCwiczeniowa;
import java.util.List;
import org.testng.annotations.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Ja
 */
public class GrupaCwiczeniowaTest {

    @Test
    public void test1() {
        //GrupaCwiczeniowa.add("PSI2");
        List<GrupaCwiczeniowa> grupyCw = GrupaCwiczeniowa.getAll();
        for (GrupaCwiczeniowa grupaCw : grupyCw) {
            System.out.println("GrupaCwiczeniowa{" + "id=" + grupaCw.getId() + ", nazwa=" + grupaCw.getNazwa() + ", przedmiot=" + grupaCw.getPrzedmiot().getNazwa() + '}');
        }
        /*GrupaCwiczeniowa.delete(3);
         GrupaCwiczeniowa grupaCw = new GrupaCwiczeniowa();
         grupaCw.setId(3);
         grupaCw.setNazwa("PSI3");
         GrupaCwiczeniowa.edit(grupaCw);*/
    }
}
