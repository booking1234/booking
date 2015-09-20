/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.trial.server;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import net.cbtltd.server.MonitorService;

/** 
 * The Class BackupService performs period backups. 
 * @see <pre>http://idiot-howto.blogspot.com/2008/05/backup-mysql-database-from-java-part-2.html</pre>
 */
public class BackupService {

	//TODO:
	//	public void run(String host, String user, String pw) {
	//		File test = new File("c:test00.sql");
	//		FileWriter fw = null;
	//		try {
	//			fw = new FileWriter(test);
	//		} catch (IOException ex) {
	//		}
	//		try {
	//			Process child = Runtime.getRuntime().exec(
	//					"mysqldump -h" + host + " -u" + user + " -p" + pw
	//					+ " dump_test");
	//			// rt.exec("mysqldump -uroot -pmypassword db_mydatabase > "+route);
	//
	//			InputStream in = child.getInputStream();
	//			InputStreamReader xx = new InputStreamReader(in, "utf8");
	//			char[] chars = new char[1024];
	//			int ibyte = 0;
	//			while ((ibyte = xx.read(chars)) > 0) {
	//				fw.write(chars);
	//			}
	//			fw.close();
	//		} catch (IOException e) {
	//		}
	//
	//	}

	private int BUFFER = 10485760;

	private String getData(String host, String port, String user,
			String password, String db) throws Exception {
		StringBuffer temp = new StringBuffer();
		try {
			Process run = Runtime.getRuntime().exec(
					"mysqldump --host=" + host + " --port=" + port + " --user="
							+ user + " --password=" + password
							+ " --compact --complete-insert --extended-insert "
							+ "--skip-comments --skip-triggers " + db);
			InputStream in = run.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			int count;
			char[] cbuf = new char[BUFFER];
			while ((count = br.read(cbuf, 0, BUFFER)) != -1)
				temp.append(cbuf, 0, count);
			br.close();
			in.close();
		}
		catch (Throwable x) {MonitorService.log(x);}
		return temp.toString();
	}

	private String getRoutine(String host, String port, String user,
			String password, String db) throws Exception {
		Process run = Runtime.getRuntime().exec(
				"mysqldump --host=" + host + " --port=" + port + " --user="
						+ user + " --password=" + password
						+ " --compact --skip-comments --no-create-info "
						+ "--no-data --routines " + db);
		InputStream in = run.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		StringBuffer temp = new StringBuffer();
		int count;
		char[] cbuf = new char[BUFFER];
		while ((count = br.read(cbuf, 0, BUFFER)) != -1)
			temp.append(cbuf, 0, count);
		br.close();
		in.close();
		return temp.toString();
	}

	/**
	 * Run the backup.
	 */
	public void run() {
		try {
			byte[] data = getData("localhost", "3306", "root", "bendagasse", "lucid").getBytes();
			//byte[] routine = getRoutine("localhost", "3306", "mansoor", "mansoor", "books").getBytes();
			File filedst = new File("d:\\backup.zip");

			FileOutputStream dest = new FileOutputStream(filedst);
			ZipOutputStream zip = new ZipOutputStream(
					new BufferedOutputStream(dest));
			zip.setMethod(ZipOutputStream.DEFLATED);
			zip.setLevel(Deflater.BEST_COMPRESSION);

			zip.putNextEntry(new ZipEntry("data.sql"));
			zip.write(data);

			//zip.putNextEntry(new ZipEntry("routine.sql"));
			//zip.write(routine);

			zip.close();
			dest.close();
			//TODO: also for images
		}
		catch (Exception x) {MonitorService.log(x);}
	}
}
