package cs5414.bank.network;

import cs5414.bank.message.FailureDetectorMessage;
import cs5414.bank.network.Configurator;

public class FailureDetector {
	
	private Configurator config;
	
	public FailureDetector(String replica_branch) {
		config = new Configurator(replica_branch);
	}
	
	public Configurator getConfigurator() {
		return config;
	}
	
	public void handleTransition(FailureDetectorMessage message) {
		if (message.requestType == FailureDetectorMessage.RequestType.FAILED) {
			config.remove(message.node);
		} else if (message.requestType == FailureDetectorMessage.RequestType.RECOVERED) {
			config.add(message.node);
		}
	}

}
