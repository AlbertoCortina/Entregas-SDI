package com.sdi.persistence.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.sdi.dto.Task;
import com.sdi.dto.User;
import com.sdi.persistence.TaskDao;
import com.sdi.persistence.util.JdbcTemplate;
import com.sdi.persistence.util.RowMapper;

public class TaskDaoJdbcImpl implements TaskDao {
	
	public class TaskDtoMapper implements RowMapper<Task> {

		@Override
		public Task toObject(ResultSet rs) throws SQLException {
			Task t = new Task();
			
				t.setId( rs.getLong("id") );
				t.setTitle( rs.getString("title") );
				t.setComments( rs.getString("comments"));
				t.setCreated( toDate( rs.getDate( "created") ));
				t.setPlanned( toDate( rs.getDate( "planned") ));
				t.setFinished( toDate( rs.getDate( "finished") ));
				t.setCategoryId( (Long)rs.getObject("category_id") ); // may be null
				t.setUserId( rs.getLong("user_id") );
				
				return t;
		}


		private Date toDate(java.sql.Date date) throws SQLException {
			return date != null 
				? new Date( date.getTime() )
				: null;
		}

	}
	
	public class CountMapper implements RowMapper<Integer> {
		@Override
		public Integer toObject(ResultSet rs) throws SQLException {
			return rs.getInt(1);
		}		
	}

	private JdbcTemplate jdbcTemplate = new JdbcTemplate();

	@Override
	public Long save(Task dto) {
		jdbcTemplate.execute("TASK_INSERT", 
				dto.getTitle(),
				dto.getComments(),
				dto.getCreated(),
				dto.getPlanned(),
				dto.getFinished(),
				dto.getCategoryId(),
				dto.getUserId()
			);
		return jdbcTemplate.getGeneratedKey();
	}

	@Override
	public int update(Task dto) {
		return jdbcTemplate.execute("TASK_UPDATE", 
				dto.getTitle(),
				dto.getComments(),
				dto.getCreated(),
				dto.getPlanned(),
				dto.getFinished(),
				dto.getCategoryId(),
				dto.getUserId(),
				
				dto.getId()
		);
	}

	@Override
	public int delete(Long id) {
		return jdbcTemplate.execute("TASK_DELETE", id);
	}

	@Override
	public Task findById(Long id) {
		return jdbcTemplate.queryForObject(
				"TASK_FIND_BY_ID", 
				new TaskDtoMapper(), 
				id
			);
	}

	@Override
	public List<Task> findAll() {
		return jdbcTemplate.queryForList(
				"TASK_FIND_ALL", 
				new TaskDtoMapper()
			);
	}

	@Override
	public int deleteAllFromUserId(Long userId) {
		return jdbcTemplate.execute("TASK_DELETE_BY_USER_ID", userId);
	}

	@Override
	public int deleteAllFromCategory(Long catId) {
		return jdbcTemplate.execute("TASK_DELETE_BY_CATEGORY_ID", catId);
	}
	
	@Override
	public int deleteAll() {
		return jdbcTemplate.execute("TASK_DELETEALL");
	}

	@Override
	public List<Task> findByUserId(Long userId) {
		return jdbcTemplate.queryForList(
				"TASK_FIND_BY_USER_ID", 
				new TaskDtoMapper(),
				userId
			);
	}

	@Override
	public List<Task> findInboxTasksByUserId(Long userId) {
		return jdbcTemplate.queryForList(
				"TASK_FIND_INBOX_BY_USER_ID", 
				new TaskDtoMapper(),
				userId
			);
	}

	@Override
	public List<Task> findTodayTasksByUserId(Long userId) {
		return jdbcTemplate.queryForList(
				"TASK_FIND_TODAY_BY_USER_ID", 
				new TaskDtoMapper(),
				userId
			);
	}

	@Override
	public List<Task> findWeekTasksByUserId(Long userId) {
		return jdbcTemplate.queryForList(
				"TASK_FIND_WEEK_BY_USER_ID", 
				new TaskDtoMapper(),
				userId
			);
	}

	@Override
	public List<Task> findTasksByCategoryId(Long catId) {
		return jdbcTemplate.queryForList(
				"TASK_FIND_UNFINISHED_BY_CATEGORY_ID", 
				new TaskDtoMapper(),
				catId
			);
	}

	@Override
	public List<Task> findFinishedTasksByCategoryId(Long catId) {
		return jdbcTemplate.queryForList(
				"TASK_FIND_FINISHED_BY_CATEGORY_ID", 
				new TaskDtoMapper(),
				catId
			);
	}

	@Override
	public List<Task> findFinishedTasksInboxByUserId(Long userId) {
		return jdbcTemplate.queryForList(
				"TASK_FIND_FINISHED_INBOX_BY_USER_ID", 
				new TaskDtoMapper(),
				userId
			);		
	}
	
	@Override
	public int tasksCompleted(User user) {
		return jdbcTemplate.queryForObject("USER_TASKS_COMPLETED", new CountMapper(), user.getId());
	}

	@Override
	public int tasksCompletedDelayed(User user) {
		return jdbcTemplate.queryForObject("USER_TASKS_COMPLETED_DELAYED", new CountMapper(), user.getId());
	}

	@Override
	public int tasksPlanned(User user) {
		return jdbcTemplate.queryForObject("USER_TASKS_PLANNED", new CountMapper(), user.getId());
	}

	@Override
	public int tasksNotPlanned(User user) {
		return jdbcTemplate.queryForObject("USER_TASKS_NOT_PLANNED", new CountMapper(), user.getId());
	}
}