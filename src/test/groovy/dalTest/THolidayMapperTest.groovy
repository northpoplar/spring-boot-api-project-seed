package dalTest

import com.company.project.Application
import com.dcf.iqunxing.dal.dao.THolidayMapper
import com.dcf.iqunxing.dal.po.THoliday
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.genesis.Gen
import spock.genesis.generators.Generator
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Stepwise
import spock.lang.Subject
import spock.lang.Title

import static spock.genesis.Gen.integer
import static spock.genesis.Gen.string

/**
 * Created by zhoucl on 2018/1/15.
 */
@SpringBootTest(classes = Application.class)
@Title("测试的标题：使用spock进行测试")
@Narrative("""大段描述--带有回车换行符
的大段描述""")
//@Subject(com.dcf.iqunxing.dal.dao.THolidayMapper.class) //待测接口
@Stepwise //当测试方法间存在依赖关系时，表明这些方法将按照其在源代码中的编写顺序执行
class THolidayMapperTest extends Specification {
    @Autowired
    THolidayMapper tHolidayMapper;

    def setup(){

    }

    def "随机新增一个date【#randomHoliday.date】"(){
        given: "随机产生的date【#randomHoliday.date】"
        when:
        def insertResult = tHolidayMapper.insertSelective(randomHoliday);
        then:
        def savedHoliday = tHolidayMapper.selectOne (randomHoliday);
        savedHoliday.getDate() == randomHoliday.getDate();


        where: "随机创建一个日期【#randomHoliday.date】"
        randomHoliday << getHoliday().take(1)
    }

    Generator getHoliday() {
        return Gen.type(THoliday,
                //id: integer(1,12000),
                date: string(32),
                remark: string(32),
        )
    }
}
