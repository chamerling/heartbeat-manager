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
package notifiers;

import java.util.List;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import play.Play;
import play.libs.Mail;
import play.mvc.Mailer;

import models.Host;

/**
 * 
 * @author chamerling
 *
 */
public class Mails extends Mailer {
	
	public static final void failures(List<Host> hosts) throws EmailException {
		System.out.println("Sending failure mail...");
		if (hosts == null || hosts.size() == 0) {
			return;
		}
		
		String msg = "";
		if (hosts.size() == 1) {
			msg = "The is one unreachable host!";
		} else {
			msg = String.format("There are %d unreachable hosts!!!", hosts.size());
		}
		
		msg = msg + "/n";
		
		setFrom(Play.configuration.getProperty("mail.smtp.user"));
		addRecipient(Play.configuration.getProperty("heartbeat.recipient"));
		setSubject("HeartBeat Failure Report");
		send(hosts);
	}
	
}
