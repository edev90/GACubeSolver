# GACubeSolver
A genetic algorithms based Rubik's Cube solver written in Java

This is a genetic algorithms based rubik cube solver written in Java (utilizing Swing for the UI).

The ultimate goal of this project is to solve the classic 3x3 rubik cube purely using genetic algorithms. 

For now, the solver is only capable of solving the 2x2 mini cube, aka 'pocket cube'. When the cube is scrambled only minimally, the solve times are fairly fast (a matter of fractional seconds), while cubes scrambled to a greater degree (10+ moves) can vary greatly: anywhere from just a few seconds to several minutes.

With only a few parameter tweaks the solver is fully compatible with a 3x3. However, when tested the 3x3 usually plateus and stalls around the same solved number of pieces. It has never completely solved the entire cube during all my test runs. The hope is that optimizing the speed of the solver for the 2x2 will result in it being capable of fully solving a 3x3 and within a reasonable amount of time as well. 

Tech specs overview:
-------------------------------------

The code is written in pure Java. The UI uses Swing for the graphics. 

I wanted this to be easily runnable for anyone with a standard Java runtime - no fancy 3rd party libs or dependencies are required. No other prerequisites are needed... of course other than a Java runtime as mentioned above. As such, the 3D graphics aren't true 3D. The cube is an image projected in an isometric fashion - with each piece colors overlaying transparent parts of the images. For further details about how this 3D effect is achieved, keep reading below.

How the 3D cube is displayed: 

For every possible layer/side rotation of the cube there are a few images representing what that rotation will look like at a given point in time during rotation []. These frames are stored in PNG format. Specific ARGB (alpha,red,green,blue) values signal whether that pixel is part of a cube piece that should be filled in with the appropriate "sticker" color at any point in time. 

I made the frames in POV-Ray, but they can be made and look like virtually anything you want as long as you follow a couple things:

- To encode an ARGB value that is a placeholder to accept a color from a cube piece, the color must be encoded in the following way: It must have an alpha channel of 50% (128 decimal or 0x80 hexadecimal) this is what signals to the decoder that this is a placeholder pixel that will be filled and overriden by a cube piece color at runtime. The Red channel is ignored so that value can be completely random or whatever you want. Green stores the 'mode' and peice orientation information. The mode is there in case and/or someone else determines a more efficient encoding scheme in the future, but want to mantain the original one for backwards compatibility. It can be left at 0 right now. Blue stores the y, x, and z information. RGBEncoder.java contains routines for decoding and encoding the colors. Reference that class for more detail.

- The images must be in PNG format. With minor changes to the code you could get other formats to work fine - but whatever format you need needs to be stored using a form of lossless compression, otherwise the ARGB codes will be decoded at a slightly different value than what you saved into it, and it will likely color the cube incorrectly.
