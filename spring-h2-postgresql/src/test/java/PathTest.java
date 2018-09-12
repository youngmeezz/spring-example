import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.Test;

/**
 * @author zacconding
 * @Date 2018-07-05
 * @GitHub : https://github.com/zacscoding
 */
public class PathTest {
    @Test
    public void path() {
        Path path = Paths.get("E:\\dbtest");
        System.out.println(path.normalize().toString());
        System.out.println(path.toAbsolutePath());
        System.out.println(path.toUri().toString());
        //System.out.println(path.toFile().ext);
    }

}
