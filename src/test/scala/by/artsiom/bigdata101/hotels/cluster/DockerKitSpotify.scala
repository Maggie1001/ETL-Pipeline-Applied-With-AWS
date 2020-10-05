package by.artsiom.bigdata101.hotels.cluster

import com.spotify.docker.client.DefaultDockerClient
import com.whisk.docker.{DockerFactory, DockerKit}
import com.whisk.docker.impl.spotify.SpotifyDockerFactory

trait DockerKitSpotify extends DockerKit {
  protected val dockerClient =
    DefaultDockerClient.fromEnv().build()

  override implicit val dockerFactory: DockerFactory =
    new SpotifyDockerFactory(dockerClient)
}
