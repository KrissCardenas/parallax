/*
 * Copyright 2012 Alex Usachev, thothbot@gmail.com
 * 
 * This file is part of Parallax project.
 * 
 * Parallax is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 * 
 * Parallax is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License 
 * for more details.
 * 
 * You should have received a copy of the GNU General Public License along with 
 * Parallax. If not, see http://www.gnu.org/licenses/.
 */

package thothbot.parallax.loader.shared.collada.dae;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import thothbot.parallax.core.shared.Log;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;

public class DaeDocument
{
	private Document document;
	private DaeAsset asset;
	
	private List<String> scenes;
	
	private Map<String, DaeGeometry> geometries;
	private Map<String, DaeVisualScene> visualScenes;

	public DaeDocument(Document document) 
	{
		this.document = document;
		
		this.asset       = DaeAsset.parse(this);
		this.geometries  = DaeGeometry.parse(this);
		this.visualScenes = DaeVisualScene.parse(this);
		
		parseScene();
	}
	
	public Document getDocument() {
		return document;
	}	
	
	private void parseScene()
	{
		scenes = new ArrayList<String>();
		
		NodeList list = document.getElementsByTagName("instance_visual_scene");
		for (int i = 0; i < list.getLength(); i++) 
		{
			DaeElement scene = new DaeElement(list.item(i)) {		
				@Override
				public void read() {}
			};
			scenes.add(scene.readAttribute("url", true));
			Log.debug("DaeDocument() scenes" + scenes);
		}
	}
}
