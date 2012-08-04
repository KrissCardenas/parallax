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

import thothbot.parallax.core.client.gl2.enums.BlendEquationMode;
import thothbot.parallax.core.client.gl2.enums.BlendingFactorDest;
import thothbot.parallax.core.client.gl2.enums.BlendingFactorSrc;
import thothbot.parallax.core.client.textures.Texture;
import thothbot.parallax.core.shared.core.Color3f;
import thothbot.parallax.core.shared.core.Vector2f;
import thothbot.parallax.core.shared.core.Vector3f;
import thothbot.parallax.core.shared.materials.Material;

public class Sprite extends Object3D
{
	/*
	 * Alignment
	 */
	public static enum ALIGNMENT 
	{
		TOP_LEFT(1, -1),
		TOP_CENTER(0, -1),
		TOP_RIGHT(-1, -1),
		CENTER_LEFT(1, 0),
		CENTER(0, 0),
		CENTER_RIGHT(-1, 0),
		BOTTOM_LEFT(1, 1),
		BOTTOM_CENTER(0, 1),
		BOTTOM_RIGHT(-1, 1);

		double x, y;
		ALIGNMENT(double x, double y) {
			this.x = x;
			this.y = y;
		}
		
		public Vector2f get() {
			return new Vector2f(this.x, this.y);
		}
	}
	
	private Color3f color;
	private Vector3f rotation3d;
	private Texture map;
	
	private Material.BLENDING blending = Material.BLENDING.NORMAL;
	private BlendingFactorSrc blendSrc = BlendingFactorSrc.SRC_ALPHA;
	private BlendingFactorDest  blendDst = BlendingFactorDest.ONE_MINUS_SRC_ALPHA; 
	private BlendEquationMode blendEquation = BlendEquationMode.FUNC_ADD;
	
	private boolean useScreenCoordinates = true;
	private boolean mergeWith3D;
	private boolean affectedByDistance;
	private boolean scaleByViewport;
	
	private Sprite.ALIGNMENT alignment = Sprite.ALIGNMENT.CENTER;
	
	private Vector2f uvOffset;
	private Vector2f uvScale;
	
	private double opacity = 1.0;
	
	private double rotationFactor;
	
	private double z;
	
	public Sprite() 
	{
		this.color = new Color3f( 0xffffff );
		this.map = new Texture();

		this.mergeWith3D = !this.useScreenCoordinates;
		this.affectedByDistance = !this.useScreenCoordinates;
		this.scaleByViewport = !this.affectedByDistance;

		this.rotation3d = this.rotation;
		this.rotationFactor = 0;

		this.uvOffset = new Vector2f( 0, 0 );
		this.uvScale  = new Vector2f( 1, 1 );
	}
	
	public Color3f getColor() {
		return color;
	}

	public void setColor(Color3f color) {
		this.color = color;
	}

	public Texture getMap() {
		return map;
	}

	public void setMap(Texture map) {
		this.map = map;
	}

	public Material.BLENDING getBlending() {
		return blending;
	}

	public void setBlending(Material.BLENDING blending) {
		this.blending = blending;
	}

	public BlendingFactorSrc getBlendSrc() {
		return blendSrc;
	}

	public void setBlendSrc(BlendingFactorSrc blendSrc) {
		this.blendSrc = blendSrc;
	}

	public BlendingFactorDest getBlendDst() {
		return blendDst;
	}

	public void setBlendDst(BlendingFactorDest blendDst) {
		this.blendDst = blendDst;
	}

	public BlendEquationMode getBlendEquation() {
		return blendEquation;
	}

	public void setBlendEquation(BlendEquationMode blendEquation) {
		this.blendEquation = blendEquation;
	}

	public Sprite.ALIGNMENT getAlignment() {
		return alignment;
	}

	public void setAlignment(Sprite.ALIGNMENT alignment) {
		this.alignment = alignment;
	}

	public Vector2f getUvOffset() {
		return uvOffset;
	}

	public void setUvOffset(Vector2f uvOffset) {
		this.uvOffset = uvOffset;
	}

	public Vector2f getUvScale() {
		return uvScale;
	}

	public void setUvScale(Vector2f uvScale) {
		this.uvScale = uvScale;
	}

	public double getOpacity() {
		return opacity;
	}

	public void setOpacity(double opacity) {
		this.opacity = opacity;
	}

	public double getRotationFactor() {
		return rotationFactor;
	}

	public void setRotationFactor(double rotation) {
		this.rotationFactor = rotation;
	}
	
	public boolean isUseScreenCoordinates() {
		return useScreenCoordinates;
	}

	public void setUseScreenCoordinates(boolean useScreenCoordinates) {
		this.useScreenCoordinates = useScreenCoordinates;
	}

	public boolean isMergeWith3D() {
		return mergeWith3D;
	}

	public void setMergeWith3D(boolean mergeWith3D) {
		this.mergeWith3D = mergeWith3D;
	}
	
	public boolean isAffectedByDistance() {
		return affectedByDistance;
	}

	public void setAffectedByDistance(boolean affectedByDistance) {
		this.affectedByDistance = affectedByDistance;
	}

	public boolean isScaleByViewport() {
		return scaleByViewport;
	}

	public void setScaleByViewport(boolean scaleByViewport) {
		this.scaleByViewport = scaleByViewport;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	/*
	 * Custom update matrix
	 */
	public void updateMatrix() 
	{
		this.matrix.setPosition( this.position );

		this.rotation3d.set( 0, 0, this.rotationFactor );
		this.matrix.setRotationFromEuler( this.rotation3d );

		if ( this.scale.getX() != 1 || this.scale.getY() != 1 ) 
		{
			this.matrix.scale( this.scale );
			this.boundRadiusScale = Math.max( this.scale.getX(), this.scale.getY() );
		}

		this.matrixWorldNeedsUpdate = true;
	}
}
