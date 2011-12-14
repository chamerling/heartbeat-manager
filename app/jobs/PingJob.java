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
package jobs;

import java.util.ArrayList;
import java.util.List;

import notifiers.Mails;

import models.Host;
import play.jobs.Every;
import play.jobs.Job;
import play.libs.WS;
import play.libs.WS.HttpResponse;
import play.libs.WS.WSRequest;

/**
 * 
 * @author chamerling
 * 
 */
@Every("1h")
public class PingJob extends Job<String> {

	@Override
	public void doJob() throws Exception {
		System.out.println("Let's ping all...");

		List<Host> hosts = Host.findAll();
		List<Host> failures = new ArrayList<Host>();
		for (Host host : hosts) {
			long current = System.currentTimeMillis();
			System.out.println("Calling host at " + host.hostname);
			String call = host.hostname;
			WSRequest request = WS.url(call).timeout("30s");
			HttpResponse response = null;
			host.lastPing = current;

			try {
				response = request.get();
				int status = response.getStatus();
				if (status == 200) {
					host.lastSuccess = current;
				} else {
					failures.add(host);
				}
			} catch (Exception e) {
				failures.add(host);
			}
			host.save();
		}

		if (failures.size() > 0) {
			Mails.failures(failures);
		}
	}
}
