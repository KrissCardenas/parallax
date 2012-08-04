/*
 * Copyright 2012 Alex Usachev, thothbot@gmail.com
 * 
 * This file based on the JavaScript source file of the THREE.JS project, 
 * licensed under MIT License.
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

package thothbot.parallax.core.shared.objects;

import thothbot.parallax.core.shared.core.Matrix4;

public class Bone extends Object3D
{

	public int skin;
	public Matrix4 skinMatrix;
	public Matrix4 identityMatrix;

	public Bone() 
	{
		this.skinMatrix = new Matrix4();
	}

	public Bone(int belongsToSkin) 
	{
		this();
		this.skin = belongsToSkin;		
	}

	public void update( Matrix4 parentSkinMatrix, boolean forceUpdate ) 
	{
		// update local
		if ( this.matrixAutoUpdate )
			this.updateMatrix();

		// update skin matrix
		if ( forceUpdate || this.matrixWorldNeedsUpdate ) 
		{

			if( parentSkinMatrix != null )
				this.skinMatrix.multiply( parentSkinMatrix, this.matrix );
			else
				this.skinMatrix.copy( this.matrix );

			this.matrixWorldNeedsUpdate = false;
			forceUpdate = true;
		}

		// update children 
		for ( DimensionalObject children : this.getChildren()) 
		{
			Bone bone = (Bone) children;
			bone.update( this.skinMatrix, forceUpdate );
		}
	}
}
