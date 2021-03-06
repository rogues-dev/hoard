package io.github.roguesdev.hoard.serialization

import com.squareup.moshi.Types
import io.github.roguesdev.hoard.test.model.Person
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification
import spock.lang.Unroll

import java.security.Security

class AesAdapterSpec extends Specification {

  @Rule TemporaryFolder dir

  void setup() {
    Security.addProvider(new BouncyCastleProvider())
  }

  @Unroll void "should write #expected from disk and then read it back"() {
    given:
    def classUnderTest = new AesSerializer<>('TestKey' as char[], 'TestSalt' as byte[])
    def file = dir.newFile('testFile.encrypt')
    def fileInputStream = new FileInputStream(file)
    def fileOutputStream = new FileOutputStream(file)

    when:
    classUnderTest.serialize(type, expected, fileOutputStream)
    def returnValue = classUnderTest.deserialize(type, fileInputStream)

    then:
    returnValue == expected


    where:
    type                                     | expected
    String                                   | 'hello world'
    Boolean                                  | true
    Integer                                  | 10 as Integer
    Person                                   | new Person(firstName: 'Test', lastName: 'Testerson')
    Types.newParameterizedType(List, Person) | [new Person(firstName: 'Test', lastName: 'Testerson')]
  }
}
