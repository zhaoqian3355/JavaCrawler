package pri.wf.crawler.service;


import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.modelmapper.ModelMapper;

import pri.wf.crawler.dao.TrainDataDao;
import pri.wf.crawler.dto.TrainDataDto;
import pri.wf.crawler.entity.TrainData;

public class TrainDataService {
	public boolean insert(List<TrainDataDto> dtos){
		boolean result=true;
		try {
			if (dtos!=null&&dtos.size()>0) {
				String resource = "pri/wf/mybatis/config/mybatis-config.xml";
				Reader reader= Resources.getResourceAsReader(resource);
				SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
				SqlSessionFactory factory = builder.build(reader);
				SqlSession session = factory.openSession();
				TrainDataDao trainDataDao=session.getMapper(TrainDataDao.class);
				ModelMapper modelMapper = new ModelMapper();
				dtos.forEach(dto->{
					TrainData trainData=modelMapper.map(dto, TrainData.class);
					trainDataDao.insert(trainData);
				});
				session.commit();
				session.close();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
			result=false;
		}
		
		return result;
	}
	
}
