# MAAS - Minecraft as a Service

MAAS is a simple web application to assist you in launching a
Minecraft server On-Demand. It sets up an [EC2] instance with the
necessary software to run a Minecraft server, optionally pre-loads it
with a saved game that you specify and any of the supported server
add-ons and then tells you where to point your Minecraft to connect to
it.

[EC2]: https://aws.amazon.com/ec2


## Save Games

MAAS can handle storing and loading saved games too, in case you don't
want to start from scratch every time. There are several different
ways to handle this.


### Local File Upload -TODO

You can choose to upload a local zip file of your world folder before
you start the server. Then, when the server shuts down, a zip file of
the new world folder with all of the changes you have made while
playing will be available to download from the activation website.

This is the slowest of the options, but it's also the simplest and cheapest.


### S3 Storage - TODO

You can also elect to have the world stored in Amazon's file storage,
[S3]. This is also very economical, but significantly faster than
uploading a file from your own computer. Most consumer internet
connections have a very slow upload speed, and when the files are
stored in S3, they are stored in the same AWS region as the EC2
instance is spun up, so the bandwidth for downloading is quite high.

With this method, the save is then uploaded to S3 at the end of each
session. You can optionally enable backups, so that every play
session is actually a snapshot of the world which you can return to at
any time (if one of the people you invite turns out to be a griefer
for instance).

[S3]: https://aws.amazon.com/s3/


### EBS Storage - TODO

The third option is mainly for when you want to enable other software
that also stores information on the harddrive; especially if it takes
a long time to compute. For instance, [MapCrafter] requires extra hard
drive space and is not trivial to run for large Minecraft maps.

[MapCrafter]: http://mapcrafter.org/index

This creates a virtual hard-disk that stores both your Minecraft save
and any other disk files that need to be associated with it. This
method is also correspondingly more expensive since the EBS volume is
a constant cost, even when not in use.


## Requirements

- [AWS] Credentials

[AWS]: https://aws.amazon.com/


## Usage

Run this app locally with `lein run` (you'll need to install
[Leiningen] first) or deploy it to [Heroku][heroku],
then hit `Launch Server` on the main page. Once the server has been
created and fully set up, the page will tell you what the IP address
of your new Minecraft server is.

[Leiningen]: http://www.leiningen.com
[heroku]: http://www.heroku.com


## License

Copyright © Geoff Shannon 2014

Distributed under the Eclipse Public License.
