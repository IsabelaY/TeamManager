package com.xhizors.TeamManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TeamManagerReader {
	
	private TeamManager instance;
	private ArrayList<String> worlds;
	
	public TeamManagerReader(TeamManager instance) {
		this.instance = instance;
	}
	
	public ArrayList<String> readWorldFile() {
		worlds = new ArrayList<String>();
		ArrayList<String> lines = readFileLines("worlds");
		
		for (String line : lines) {
			String[] content = line.split("#");
			if (content[0] != ""  && instance.getServer().getWorld(content[0]) != null && !worlds.contains(content[0])) {
				worlds.add(content[0]);
				TeamManager.log.info("[" + TeamManager.name + "] load world " + content[0]);
			}
		}
		
		return worlds;
	}
	
	private ArrayList<String> readFileLines(String fileName) {
		ArrayList<String> lines = new ArrayList<String>();
		File file = new File("plugins" + File.separator + "TeamManager" + File.separator + fileName + ".txt");
		File dir = new File(file.getParent());
		if (!dir.exists()){
            dir.mkdir();
        }
		if(!file.exists()){
            try {
                file.createNewFile();
                BufferedWriter out = new BufferedWriter(new FileWriter(file));
                TeamManager.log.info("[" + TeamManager.name + "] creating world file");
                out.write("#This is a comment line");
                out.newLine();
                out.write("#Put your worlds here where you want to use the TeamManager");
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
                TeamManager.log.warning("[" + TeamManager.name + "] could not create world file");
            }
        }
		try {
			BufferedReader input =  new BufferedReader(new FileReader(file));
			try {
				String line = null;
				while ((line = input.readLine()) != null) {
					lines.add(line);
				}
			} finally {
				input.close();
			}
		} catch (IOException ex){
			ex.printStackTrace();
		}
		return lines;
	}
}
