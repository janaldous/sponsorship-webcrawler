package com.janaldous.sponsorshipwebcrawler.webcrawler.repository;

import java.util.ArrayList;
import java.util.List;

import com.janaldous.sponsorshipwebcrawler.webcrawler.domain.Job;

import lombok.NonNull;

public class JobOpeningRespository {
	
	private List<Job> list;
	
	public JobOpeningRespository() {
		list = new ArrayList<>();
	}
	
	public void save(@NonNull List<Job> jobOpenings) {
		list.addAll(jobOpenings);
	}

	public List<Job> findAll() {
		return list;
	}
	
	public void clear() {
		list.clear();
	}

}
