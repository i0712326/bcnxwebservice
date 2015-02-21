package com.bcnx.web.app.service;

import java.sql.Timestamp;
import java.util.List;

import com.bcnx.web.app.service.entity.Bulletin;

public interface BulletinService {
	public void save(Bulletin bulletin);
	public List<Bulletin> getBulletin(int first, int max);
	public List<Bulletin> getBulletin(Timestamp start, Timestamp end, int first, int max);
}
