package com.ketang.dao.ser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import com.ketang.entity.ser.Note;

public interface NoteDao extends JpaRepository<Note,Integer>,JpaSpecificationExecutor<Note> {
	
	@Query(value="select * from t_note where id = ?1",nativeQuery = true)
	public Note findId(Integer id);

}
