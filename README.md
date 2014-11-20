# MAAS - Minecraft as a Service

MAAS is a simple web application to assist you in launching a
Minecraft server On-Demand. It sets up an [EC2] micro instance
with the necessary software to run a Minecraft server and then tells
you the server's IP address once it is running.

[EC2]: https://aws.amazon.com/ec2


## Save Games

You can opt to either store the game world's folder in [S3], or upload
a world folder when you start the server, and download the new world
when you shut it down again.

[S3]: https://aws.amazon.com/s3/


## Requirements

- [AWS] Credentials

[AWS]: https://aws.amazon.com/


## Usage

Run this app locally with `lein run` or deploy it to [Heroku][heroku],
then hit `Launch Server` on the main index. It will tell you what the
IP address of your new minecraft server is.

[heroku]: http://www.heroku.com


## License

Copyright © Geoff Shannon 2014

Distributed under the Eclipse Public License.
