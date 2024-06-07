/*
 * Copyright 2014 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hibernate.bugs;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.testing.junit4.BaseCoreFunctionalTestCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using its built-in unit test framework.
 * Although ORMStandaloneTestCase is perfectly acceptable as a reproducer, usage of this class is much preferred.
 * Since we nearly always include a regression test with bug fixes, providing your reproducer using this method
 * simplifies the process.
 *
 * What's even better?  Fork hibernate-orm itself, add your test case directly to a module's unit tests, then
 * submit it as a PR!
 */
public class ORMUnitTestCase extends BaseCoreFunctionalTestCase {

	// Add your entities here.
	@Override
	protected Class[] getAnnotatedClasses() {
		return new Class[] {
//				Foo.class,
//				Bar.class
				Company.class,
				Event.class,
				EventDetail.class,
				EventSingleId.class,
				EventDetailSingleId.class
		};
	}

	// If you use *.hbm.xml mappings, instead of annotations, add the mappings here.
	@Override
	protected String[] getMappings() {
		return new String[] {
//				"Foo.hbm.xml",
//				"Bar.hbm.xml"
		};
	}
	// If those mappings reside somewhere other than resources/org/hibernate/test, change this.
	@Override
	protected String getBaseForMappings() {
		return "org/hibernate/test/";
	}

	// Add in any settings that are specific to your test.  See resources/hibernate.properties for the defaults.
	@Override
	protected void configure(Configuration configuration) {
		super.configure( configuration );

		configuration.setProperty( AvailableSettings.SHOW_SQL, Boolean.TRUE.toString() );
		configuration.setProperty( AvailableSettings.FORMAT_SQL, Boolean.TRUE.toString() );
		//configuration.setProperty( AvailableSettings.GENERATE_STATISTICS, "true" );
	}

	// Add your tests, using standard JUnit.

	@Test
	public void testJoin() throws Exception {
		Company company1 = persistData1();
		Company company2 = persistData2();

		//Actual Test
		Session s = openSession();
		Transaction tx = s.beginTransaction();

		//Composite Id Query
		Query query = s.createQuery("select ed from EventDetail ed where ed.event.company  = :company");
		query.setParameter("company", company2);
		List<EventDetail> eventDetailListResult = query.list();
		System.out.println(eventDetailListResult.size());
		Assert.assertEquals(1, eventDetailListResult.size());

		//Single Id Query
		query = s.createQuery("select ed from EventDetailSingleId ed where ed.event.company  = :company");
		query.setParameter("company", company1);
		List<EventDetail> eventDetailSingleIdListResult = query.list();
		System.out.println(eventDetailSingleIdListResult.size());
		Assert.assertEquals(1, eventDetailSingleIdListResult.size());

		tx.commit();
		s.close();


	}

	private Company persistData1() {
		//Persist Data Single Id
		Session s = openSession();
		Transaction tx = s.beginTransaction();
		Company company = new Company();
		company.setId(1L);
		s.save(company);

		EventSingleId event = new EventSingleId();
		event.setId(1L);
		event.setCompany(company);
		event.setEventType("old event type");
		s.save(event);

		EventDetailSingleId eventDetail = new EventDetailSingleId();
		eventDetail.setId(1L);
		eventDetail.setCompany(company);
		//eventDetail.setEvent(event);

		Set<EventDetailSingleId> eventDetailSet = new HashSet<>();
		eventDetailSet.add(eventDetail);
		event.setEventDetailSet(eventDetailSet);

		s.save(eventDetail);
		s.save(event);

		tx.commit();
		s.close();
		return company;
	}

	private Company persistData2() {
		//Persist Data Composite Id
		Session s = openSession();
		Transaction tx = s.beginTransaction();
		Company company = new Company();
		company.setId(2L);
		s.save(company);

		Event event = new Event();
		event.setId(1L);
		event.setCompany(company);
		event.setEventType("old event type");
		s.save(event);

		EventDetail eventDetail = new EventDetail();
		eventDetail.setId(1L);
		eventDetail.setCompany(company);
		//eventDetail.setEvent(event);

		Set<EventDetail> eventDetailSet = new HashSet<>();
		eventDetailSet.add(eventDetail);
		event.setEventDetailSet(eventDetailSet);

		s.save(eventDetail);
		s.save(event);

		tx.commit();
		s.close();
		return company;
	}
}
