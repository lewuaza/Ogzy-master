
import org.database.models.GrupaStudent;
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
public class GrupaStudentTest {

    @Test
    public void test1() {
        //GrupaStudent.add(99, 99);

        List<Integer> studentIds = GrupaStudent.getAllStudentIdsForGroup();
        for (int id : studentIds) {
            System.out.println("Id studenta: " + id);
        }
        /*
         GrupaStudent.delete(99, 99);
         studentIds = GrupaStudent.getAllStudentIdsForGroup();
         for (int id : studentIds) {
         System.out.println("Id studenta: " + id);
         }*/
    }

    @Test
    public void test2() {
        List<Integer> groupIds = GrupaStudent.getAllStudentIdsForGroup();
        for (int id : groupIds) {
            System.out.println("Id grupy: " + id);
        }
    }
}
