package jobs;

/*
 * Copyright 2011 Christophe Hamerling
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.util.ArrayList;
import java.util.List;

import notifiers.Mails;

import org.apache.commons.mail.SimpleEmail;

import models.Host;
import play.Play;
import play.jobs.Every;
import play.jobs.Job;
import play.libs.Mail;

/**
 * @author chamerling
 * 
 */
@Every("24h")
public class ReportingJob extends Job<String> {

	/**
	 * Get all the reports and generate a summary to be sent to the manager
	 */
	@Override
	public void doJob() throws Exception {
		System.out.println("Fire Report");
		List<Host> result = new ArrayList<Host>();
		List<Host> hosts = Host.findAll();
		for (Host host : hosts) {
			if (host.lastPing != host.lastSuccess) {
				result.add(host);
			}
		}

		if (result.size() > 0) {
			Mails.failures(result);
		}
	}
}
