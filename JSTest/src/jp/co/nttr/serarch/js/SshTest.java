package jp.co.nttr.serarch.js;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

import sos.spooler.Job_impl;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SshTest extends Job_impl{

		private String userid;
		private String password;
		private String keypath;
		private String keypass;

		private String hostname;
		private String portnum;
/*
		public void main() throws JSchException, IOException{

	    	userid = "t-nino";
	        password = "PNNC205j";
	        hostname = "hk2-lr990005ag.super-goo.com";
	        portnum = "22";
	        keypath = "C:\\Users\\t-nino\\Documents\\証明書\\秘密鍵\t-nino-4pod";
	        keypass = "PNNC205j";


	        doProc();

		}
*/

	    public boolean spooler_process() throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	    	/*
	    	userid = spooler_task.params().value("SSH_USERID");
	        password = spooler_task.params().value("SSH_PASSWORD");
	        hostname = spooler_task.params().value("SSH_HOSTNAME");
	        portnum = spooler_task.params().value("SSH_PORT");
	        keypath = spooler_task.params().value("SSH_KEY_PATH");
	        keypass = spooler_task.params().value("SSH_KEY_PASS");

	        */


	    	userid = "t-nino";
	        password = "PNNC205j";
	        hostname = "hk2-lr990005ag.super-goo.com";
	        portnum = "22";
	        keypath = "C:\\Users\\t-nino\\Documents\\証明書\\秘密鍵\t-nino-4pod";
	        keypass = "PNNC205j";


	        doProc();

	    	return false;

	    }

		public void doProc() throws JSchException, IOException {
			JSch jsch=new JSch();

			// HostKeyチェックを行わない
			Hashtable config = new Hashtable();
			config.put("StrictHostKeyChecking", "no");
			JSch.setConfig(config);

			jsch.addIdentity(keypath,keypass);

			// known_hosts を設定して HostKey チェックをおこなう場合
			//String knownhost = "/home/****/.ssh/known_hosts";		// known_hosts ファイルのフルパス
			//jsch.setKnownHosts(knownhost);

			// connect session
			Session session=jsch.getSession(userid, hostname, 22);
			session.setPassword(password);
			session.connect();

			// exec command remotely
			String command="ls -l";
			ChannelExec channel=(ChannelExec)session.openChannel("exec");
			channel.setCommand(command);
			channel.connect();

			// get stdout
			InputStream in = channel.getInputStream();
			byte[] tmp=new byte[1024];
			while (true) {
				while(in.available()>0){
					int i=in.read(tmp, 0, 1024);
					if (i<0) break;
					System.out.print(new String(tmp, 0, i));
				}
				if (channel.isClosed()) {
					System.out.println("exit-status: "+channel.getExitStatus());
					break;
				}
				try { Thread.sleep(1000); } catch (Exception ee) {}
			}
			channel.disconnect();
			session.disconnect();

		}
}
