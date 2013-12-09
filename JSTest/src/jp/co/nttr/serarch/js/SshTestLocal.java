package jp.co.nttr.serarch.js;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

public class SshTestLocal{

		private static String userid;
		private static String password;
		private static String keypath;
		private static String keypass;

		private static String local_hostname;
		private static int local_portnum;

		private static String remote_hostname;
		private static int remote_portnum;

		private static String command;


		public static void main(String[] args) throws JSchException, IOException{

	    	userid = "t-nino";
	        password = "PNNC205j";
	        local_hostname = "hk2-lr990005ag.super-goo.com";
	        local_portnum = 22;

	        remote_hostname = "hk2-lr700321.super-goo.com";
	        remote_portnum = 22;

	        keypath = "C:\\Users\\t-nino\\Documents\\証明書\\秘密鍵\\t-nino-openssh-dsa";
	        keypass = "PNNC205j";

	        //command = "ssh hk2-lr700321";
	        command = "ls -la";


	        doProc();

		}


		public static void doProc() throws JSchException, IOException {
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
			Session session=jsch.getSession(userid, local_hostname, local_portnum);
			session.setPassword(password);

			LocalUserInfo lui = new LocalUserInfo();
			session.setUserInfo(lui);
			session.connect();
			
			session.
			
			//香港へポートフォワード///ポートフォワードできない？
			int a=  session.setPortForwardingL(local_portnum,remote_hostname,remote_portnum);


			// exec command remotely

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
		static class LocalUserInfo implements UserInfo{

			public String getPassphrase() {
				// TODO 自動生成されたメソッド・スタブ
				return null;
			}

			public String getPassword() {
				// TODO 自動生成されたメソッド・スタブ
				return password;
			}

			public boolean promptPassphrase(String arg0) {
				// TODO 自動生成されたメソッド・スタブ
				return true;
			}

			public boolean promptPassword(String arg0) {
				// TODO 自動生成されたメソッド・スタブ
				return true;
			}

			public boolean promptYesNo(String arg0) {
				// TODO 自動生成されたメソッド・スタブ
				return true;
			}

			public void showMessage(String arg0) {
				// TODO 自動生成されたメソッド・スタブ

			}

		}
}
