package bg.forcar.api.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "mailbox")
public class MailEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "seq_gen_mail", sequenceName = "s_mails", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen_mail")
    @Column(name = "mail_id")
    private Integer mailId;

    @Column(name = "sender")
    private String sender;

    @Column(name = "receivers")
    private String receivers;

    @Column(name = "subject")
    private String subject;

    @Column(name = "content")
    private String content;
}
