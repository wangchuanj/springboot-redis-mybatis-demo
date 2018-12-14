package com.github.demo.core;


import org.springframework.beans.factory.InitializingBean;

import java.util.Observable;

public class SubjectObservable extends Observable implements InitializingBean {

	@Override
	public void afterPropertiesSet() throws Exception {

	}
}
