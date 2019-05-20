package scommons.react.test.dom

import org.scalatest.time.{Millis, Span}

class AsyncTestSpecTest extends AsyncTestSpec {

  implicit override val patienceConfig: PatienceConfig = PatienceConfig(
    timeout = scaled(Span(500, Millis)),
    interval = scaled(Span(100, Millis))
  )
  
  it should "run successfully after several retries when eventually" in {
    //given
    val value = 5
    var retries = 3

    //when & then
    eventually {
      retries -= 1
      if (retries >= 0) value shouldBe 1
      else value shouldBe 5
    }
  }

  it should "fail if not succeeded within timeout when eventually" in {
    //given
    val value = 5

    //when
    val resultF = eventually {
      value shouldBe 1
    }
    
    //then
    resultF.failed.map { result =>
      result.getMessage should include ("5 was not equal to 1")
    }
  }

  it should "run successfully after delay when executeAfterDelay" in {
    //given
    val delay = 500
    val value = 5

    //when & then
    executeAfterDelay(delay) {
      value shouldBe 5
    }
  }

  it should "fail if not succeeded after delay when executeAfterDelay" in {
    //given
    val delay = 500
    val value = 5

    //when
    val resultF = executeAfterDelay(delay) {
      value shouldBe 1
    }
    
    //then
    resultF.failed.map { result =>
      result.getMessage should include ("5 was not equal to 1")
    }
  }
}
